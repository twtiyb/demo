package multiMedia.JavaMPEG;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class represents a buffered input stream which can read
 * variable length codes from MPEG-1 video streams.
 */
class BitInputStream {
    /**
     * MPEG video layers start codes
     */
    public final static int SYNC_START_CODE = 0x000001;
    public final static int PIC_START_CODE = 0x00000100;
    public final static int SLICE_MIN_CODE = 0x00000101;
    public final static int SLICE_MAX_CODE = 0x000001af;
    public final static int USER_START_CODE = 0x000001b2;
    public final static int SEQ_START_CODE = 0x000001b3;
    public final static int EXT_START_CODE = 0x000001b5;
    public final static int SEQ_END_CODE = 0x000001b7;
    public final static int GOP_START_CODE = 0x000001b8;

    /**
     * The underlying input stream
     */
    private InputStream stream;

    /**
     * The bit buffer variables
     */
    private int bitbuffer, bitcount;

    /**
     * The 32 bit buffer variables
     */
    private int buffer[], count, position;


    /**
     * Initializes the bit input stream object
     */
    public BitInputStream(InputStream inputStream) {
        stream = inputStream;
        buffer = new int[1024];
        bitbuffer = bitcount = 0;
        count = position = 0;
    }

    /**
     * Reads the next MPEG-1 layer start code
     */
    public int getCode() throws IOException {
        alignBits(8);
        while (showBits(24) != SYNC_START_CODE)
            flushBits(8);
        return getBits(32);
    }

    /**
     * Shows the next MPEG-1 layer start code
     */
    public int showCode() throws IOException {
        alignBits(8);
        while (showBits(24) != SYNC_START_CODE)
            flushBits(8);
        return showBits(32);
    }

    /**
     * Reads the next variable length code
     */
    public int getBits(int nbits) throws IOException {
        int bits;
        if (nbits <= bitcount) {
            bits = bitbuffer >>> (32 - nbits);
            bitbuffer <<= nbits;
            bitcount -= nbits;
        } else {
            bits = bitbuffer >>> (32 - nbits);
            nbits -= bitcount;
            bitbuffer = get32Bits();
            bits |= bitbuffer >>> (32 - nbits);
            bitbuffer <<= nbits;
            bitcount = 32 - nbits;
        }
        if (nbits >= 32)
            bitbuffer = 0;
        return bits;
    }

    /**
     * Shows the next variable length code
     */
    public int showBits(int nbits) throws IOException {
        int bits = bitbuffer >>> (32 - nbits);
        if (nbits > bitcount) {
            bits |= show32Bits() >>> (32 + bitcount - nbits);
        }
        return bits;
    }

    /**
     * Flushes the current variable length code
     */
    public void flushBits(int nbits) throws IOException {
        if (nbits <= bitcount) {
            bitbuffer <<= nbits;
            bitcount -= nbits;
        } else {
            nbits -= bitcount;
            bitbuffer = get32Bits() << nbits;
            bitcount = 32 - nbits;
        }
    }

    /**
     * Aligns the input stream pointer to a given boundary
     */
    public void alignBits(int nbits) throws IOException {
        flushBits(bitcount % nbits);
    }

    /**
     * Reads the next 32-bit code from the buffered stream
     */
    private int get32Bits() throws IOException {
        if (position >= count) {
            position = 0;
            for (count = 0; count < buffer.length; count++)
                buffer[count] = read32Bits();
        }
        return buffer[position++];
    }

    /**
     * Shows the next 32-bit code from the buffered stream
     */
    private int show32Bits() throws IOException {
        if (position >= count) {
            position = 0;
            for (count = 0; count < buffer.length; count++)
                buffer[count] = read32Bits();
        }
        return buffer[position];
    }

    /**
     * Reads 32-bit big endian codes from the stream
     */
    private int read32Bits() throws IOException {
        if (stream.available() <= 0)
            return SEQ_END_CODE;
        int a0 = stream.read() & 0xff;
        int a1 = stream.read() & 0xff;
        int a2 = stream.read() & 0xff;
        int a3 = stream.read() & 0xff;
        return (a0 << 24) + (a1 << 16) + (a2 << 8) + (a3 << 0);
    }
}


/**
 * Huffman VLC entropy decoder for MPEG-1 video streams. The tables
 * are from ISO/IEC 13818-2 DIS, Annex B, variable length code tables.
 */
class VLCInputStream extends BitInputStream {
    /**
     * Table B-1, variable length codes for macroblock address increments
     */
    private final static byte MBAtable[][] = {
            // 00000011xxx
            {33, 11}, {32, 11}, {31, 11}, {30, 11},
            {29, 11}, {28, 11}, {27, 11}, {26, 11},

            // 0000010xxxx
            {25, 11}, {24, 11}, {23, 11}, {22, 11},
            {21, 10}, {21, 10}, {20, 10}, {20, 10},
            {19, 10}, {19, 10}, {18, 10}, {18, 10},
            {17, 10}, {17, 10}, {16, 10}, {16, 10},

            // 0000xxxx...
            {0, 0}, {0, 0}, {0, 0}, {33, 11},
            {25, 11}, {19, 10}, {15, 8}, {14, 8},
            {13, 8}, {12, 8}, {11, 8}, {10, 8},
            {9, 7}, {9, 7}, {8, 7}, {8, 7},

            // 00xxx......
            {0, 0}, {13, 8}, {7, 5}, {6, 5},
            {5, 4}, {5, 4}, {4, 4}, {4, 4},

            // xxx........
            {0, 0}, {5, 4}, {3, 3}, {2, 3},
            {1, 1}, {1, 1}, {1, 1}, {1, 1}
    };

    /**
     * Table B-2, variable length codes for I-picture macroblock types
     */
    private final static byte IMBtable[][] = {
            // xx
            {0, 0}, {17, 2}, {1, 1}, {1, 1}
    };

    /**
     * Table B-3, variable length codes for P-picture macroblock types
     */
    private final static byte PMBtable[][] = {
            // 000xxx
            {0, 0}, {17, 6}, {18, 5}, {18, 5},
            {26, 5}, {26, 5}, {1, 5}, {1, 5},

            // xxx...
            {0, 0}, {8, 3}, {2, 2}, {2, 2},
            {10, 1}, {10, 1}, {10, 1}, {10, 1}
    };

    /**
     * Table B-4, variable length codes for B-picture macroblock types
     */
    private final static byte BMBtable[][] = {
            // 00xxxx
            {0, 0}, {17, 6}, {22, 6}, {26, 6},
            {30, 5}, {30, 5}, {1, 5}, {1, 5},
            {8, 4}, {8, 4}, {8, 4}, {8, 4},
            {10, 4}, {10, 4}, {10, 4}, {10, 4},

            // xxx...
            {0, 0}, {8, 4}, {4, 3}, {6, 3},
            {12, 2}, {12, 2}, {14, 2}, {14, 2},
    };

    /**
     * Table B-9, variable length codes for coded block patterns
     */
    private final static byte CBPtable[][] = {
            // 000000xxx
            {0, 0}, {0, 9}, {39, 9}, {27, 9},
            {59, 9}, {55, 9}, {47, 9}, {31, 9},

            // 000xxxxx.
            {0, 0}, {39, 9}, {59, 9}, {47, 9},
            {58, 8}, {54, 8}, {46, 8}, {30, 8},
            {57, 8}, {53, 8}, {45, 8}, {29, 8},
            {38, 8}, {26, 8}, {37, 8}, {25, 8},
            {43, 8}, {23, 8}, {51, 8}, {15, 8},
            {42, 8}, {22, 8}, {50, 8}, {14, 8},
            {41, 8}, {21, 8}, {49, 8}, {13, 8},
            {35, 8}, {19, 8}, {11, 8}, {7, 8},

            // 001xxxx..
            {34, 7}, {18, 7}, {10, 7}, {6, 7},
            {33, 7}, {17, 7}, {9, 7}, {5, 7},
            {63, 6}, {63, 6}, {3, 6}, {3, 6},
            {36, 6}, {36, 6}, {24, 6}, {24, 6},

            // xxxxx....
            {0, 0}, {57, 8}, {43, 8}, {41, 8},
            {34, 7}, {33, 7}, {63, 6}, {36, 6},
            {62, 5}, {2, 5}, {61, 5}, {1, 5},
            {56, 5}, {52, 5}, {44, 5}, {28, 5},
            {40, 5}, {20, 5}, {48, 5}, {12, 5},
            {32, 4}, {32, 4}, {16, 4}, {16, 4},
            {8, 4}, {8, 4}, {4, 4}, {4, 4},
            {60, 3}, {60, 3}, {60, 3}, {60, 3}
    };

    /**
     * Table B-10, variable length codes for motion vector codes
     */
    private final static byte MVtable[][] = {
            // 00000011xx
            {16, 10}, {15, 10}, {14, 10}, {13, 10},

            // 0000010xxx
            {12, 10}, {11, 10}, {10, 9}, {10, 9},
            {9, 9}, {9, 9}, {8, 9}, {8, 9},

            // 000xxxx...
            {0, 0}, {0, 0}, {12, 10}, {7, 7},
            {6, 7}, {5, 7}, {4, 6}, {4, 6},
            {3, 4}, {3, 4}, {3, 4}, {3, 4},
            {3, 4}, {3, 4}, {3, 4}, {3, 4},

            // xxx.......
            {0, 0}, {2, 3}, {1, 2}, {1, 2},
            {0, 0}, {0, 0}, {0, 0}, {0, 0}
    };

    /**
     * Table B-12, variable length codes for DC luminance sizes
     */
    private final static byte DClumtable[][] = {
            // xxx......
            {1, 2}, {1, 2}, {2, 2}, {2, 2},
            {0, 3}, {3, 3}, {4, 3}, {5, 4},

            // 111xxx...
            {5, 4}, {5, 4}, {5, 4}, {5, 4},
            {6, 5}, {6, 5}, {7, 6}, {8, 7},

            // 111111xxx
            {8, 7}, {8, 7}, {8, 7}, {8, 7},
            {9, 8}, {9, 8}, {10, 9}, {11, 9}
    };

    /**
     * Table B-13, variable length codes for DC chrominance sizes
     */
    private final static byte DCchrtable[][] = {
            // xxxx......
            {0, 2}, {0, 2}, {0, 2}, {0, 2},
            {1, 2}, {1, 2}, {1, 2}, {1, 2},
            {2, 2}, {2, 2}, {2, 2}, {2, 2},
            {3, 3}, {3, 3}, {4, 4}, {5, 5},

            // 1111xxx...
            {5, 5}, {5, 5}, {5, 5}, {5, 5},
            {6, 6}, {6, 6}, {7, 7}, {8, 8},

            // 1111111xxx
            {8, 8}, {8, 8}, {8, 8}, {8, 8},
            {9, 9}, {9, 9}, {10, 10}, {11, 10}
    };

    public final static short EOB = 64;
    public final static short ESC = 65;

    /**
     * Table B-14, variable length codes for DCT coefficients
     */
    private final static short DCTtable[][] = {
            // 000000000001xxxx
            {4609, 16}, {4353, 16}, {4097, 16}, {3841, 16},
            {774, 16}, {528, 16}, {527, 16}, {526, 16},
            {525, 16}, {524, 16}, {523, 16}, {287, 16},
            {286, 16}, {285, 16}, {284, 16}, {283, 16},

            // 00000000001xxxx.
            {10240, 15}, {9984, 15}, {9728, 15}, {9472, 15},
            {9216, 15}, {8960, 15}, {8704, 15}, {8448, 15},
            {8192, 15}, {3585, 15}, {3329, 15}, {3073, 15},
            {2817, 15}, {2561, 15}, {2305, 15}, {2049, 15},

            // 0000000001xxxx..
            {7936, 14}, {7680, 14}, {7424, 14}, {7168, 14},
            {6912, 14}, {6656, 14}, {6400, 14}, {6144, 14},
            {5888, 14}, {5632, 14}, {5376, 14}, {5120, 14},
            {4864, 14}, {4608, 14}, {4352, 14}, {4096, 14},

            // 000000001xxxx...
            {522, 13}, {521, 13}, {773, 13}, {1027, 13},
            {1282, 13}, {1793, 13}, {1537, 13}, {3840, 13},
            {3584, 13}, {3328, 13}, {3072, 13}, {282, 13},
            {281, 13}, {280, 13}, {279, 13}, {278, 13},

            // 00000001xxxx....
            {2816, 12}, {520, 12}, {772, 12}, {2560, 12},
            {1026, 12}, {519, 12}, {277, 12}, {276, 12},
            {2304, 12}, {275, 12}, {274, 12}, {1281, 12},
            {771, 12}, {2048, 12}, {518, 12}, {273, 12},

            // 0000001xxx......
            {272, 10}, {517, 10}, {1792, 10}, {770, 10},
            {1025, 10}, {271, 10}, {270, 10}, {516, 10},

            // 000xxxx.........
            {0, 0}, {272, 10}, {ESC, 6}, {ESC, 6},
            {514, 7}, {265, 7}, {1024, 7}, {264, 7},
            {263, 6}, {263, 6}, {262, 6}, {262, 6},
            {513, 6}, {513, 6}, {261, 6}, {261, 6},

            // 00100xxx........
            {269, 8}, {1536, 8}, {268, 8}, {267, 8},
            {515, 8}, {769, 8}, {1280, 8}, {266, 8},

            // xxxxx...........
            {0, 0}, {514, 7}, {263, 6}, {513, 6},
            {269, 8}, {768, 5}, {260, 5}, {259, 5},
            {512, 4}, {512, 4}, {258, 4}, {258, 4},
            {257, 3}, {257, 3}, {257, 3}, {257, 3},
            {EOB, 2}, {EOB, 2}, {EOB, 2}, {EOB, 2},
            {EOB, 2}, {EOB, 2}, {EOB, 2}, {EOB, 2},
            {256, 2}, {256, 2}, {256, 2}, {256, 2},
            {256, 2}, {256, 2}, {256, 2}, {256, 2}
    };

    /**
     * Storage for RLE run and level of DCT block coefficients
     */
    private int data[];


    /**
     * Initializes the Huffman entropy decoder for MPEG-1 streams
     */
    public VLCInputStream(InputStream inputStream) {
        super(inputStream);
        data = new int[2];
    }

    /**
     * Returns macroblock address increment codes
     */
    public int getMBACode() throws IOException {
        int code, value = 0;

    /* skip macroblock escape */
        while ((code = showBits(11)) == 15) {
            flushBits(11);
        }

    /* decode macroblock skip codes */
        while ((code = showBits(11)) == 8) {
            flushBits(11);
            value += 33;
        }

    /* decode macroblock increment */
        if (code >= 512)
            code = (code >> 8) + 48;
        else if (code >= 128)
            code = (code >> 6) + 40;
        else if (code >= 48)
            code = (code >> 3) + 24;
        else if (code >= 24)
            code -= 24;
        else
            throw new IOException("Invalid macro block address increment");
        flushBits(MBAtable[code][1]);
        return value + MBAtable[code][0];
    }

    /**
     * Returns I-picture macroblock type flags
     */
    public int getIMBCode() throws IOException {
        int code = showBits(2);
        if (code <= 0)
            throw new IOException("Invalid I-picture macro block code");
        flushBits(IMBtable[code][1]);
        return IMBtable[code][0];
    }

    /**
     * Returns P-picture macroblock type flags
     */
    public int getPMBCode() throws IOException {
        int code = showBits(6);
        if (code >= 8)
            code = (code >> 3) + 8;
        else if (code <= 0)
            throw new IOException("Invalid P-picture macro block code");
        flushBits(PMBtable[code][1]);
        return PMBtable[code][0];
    }

    /**
     * Returns B-picture macroblock type flags
     */
    public int getBMBCode() throws IOException {
        int code = showBits(6);
        if (code >= 16)
            code = (code >> 3) + 16;
        else if (code <= 0)
            throw new IOException("Invalid B-picture macro block code");
        flushBits(BMBtable[code][1]);
        return BMBtable[code][0];
    }

    /**
     * Returns coded block pattern flags
     */
    public int getCBPCode() throws IOException {
        int code = showBits(9);
        if (code >= 128)
            code = (code >> 4) + 56;
        else if (code >= 64)
            code = (code >> 2) + 24;
        else if (code >= 8)
            code = (code >> 1) + 8;
        else if (code <= 0)
            throw new IOException("Invalid block pattern code");
        flushBits(CBPtable[code][1]);
        return CBPtable[code][0];
    }

    /**
     * Returns motion vector codes
     */
    public int getMVCode() throws IOException {
        int code = showBits(10);
        if (code >= 128)
            code = (code >> 7) + 28;
        else if (code >= 24)
            code = (code >> 3) + 12;
        else if (code >= 12)
            code -= 12;
        else
            throw new IOException("Invalid motion vector code");
        flushBits(MVtable[code][1]);
        code = MVtable[code][0];
        return (getBits(1) == 0 ? code : -code);
    }

    /**
     * Returns intra coded DC luminance coefficients
     */
    public int getIntraDCLumValue() throws IOException {
        int code = showBits(9);
        if (code >= 504)
            code -= 488;
        else if (code >= 448)
            code = (code >> 3) - 48;
        else
            code >>= 6;
        flushBits(DClumtable[code][1]);
        int nbits = DClumtable[code][0];
        if (nbits != 0) {
            code = getBits(nbits);
            if ((code & (1 << (nbits - 1))) == 0)
                code -= (1 << nbits) - 1;
            return code;
        }
        return 0;
    }

    /**
     * Returns intra coded DC chrominance coefficients
     */
    public int getIntraDCChromValue() throws IOException {
        int code = showBits(10);
        if (code >= 1016)
            code -= 992;
        else if (code >= 960)
            code = (code >> 3) - 104;
        else
            code >>= 6;
        flushBits(DCchrtable[code][1]);
        int nbits = DCchrtable[code][0];
        if (nbits != 0) {
            code = getBits(nbits);
            if ((code & (1 << (nbits - 1))) == 0)
                code -= (1 << nbits) - 1;
            return code;
        }
        return 0;
    }

    /**
     * Returns inter coded DC luminance or chrominance coefficients
     */
    public int[] getInterDCValue() throws IOException {
    /* handle special variable length code */
        if (showBits(1) != 0) {
            data[0] = 0;
            data[1] = getBits(2) == 2 ? 1 : -1;
            return data;
        }
        return getACValue();
    }

    /**
     * Returns AC luminance or chrominance coefficients
     */
    public int[] getACValue() throws IOException {
        int code = showBits(16);
        if (code >= 10240)
            code = (code >> 11) + 112;
        else if (code >= 8192)
            code = (code >> 8) + 72;
        else if (code >= 1024)
            code = (code >> 9) + 88;
        else if (code >= 512)
            code = (code >> 6) + 72;
        else if (code >= 256)
            code = (code >> 4) + 48;
        else if (code >= 128)
            code = (code >> 3) + 32;
        else if (code >= 64)
            code = (code >> 2) + 16;
        else if (code >= 32)
            code >>= 1;
        else if (code >= 16)
            code -= 16;
        else
            throw new IOException("Invalid DCT coefficient code");

        flushBits(DCTtable[code][1]);

        data[0] = DCTtable[code][0] & 0xFF;
        data[1] = DCTtable[code][0] >>> 8;
        if (data[0] == ESC) {
            data[0] = getBits(6);
            data[1] = getBits(8);
            if (data[1] == 0x00)
                data[1] = getBits(8);
            else if (data[1] == 0x80)
                data[1] = getBits(8) - 256;
            else if (data[1] >= 0x80)
                data[1] -= 256;
        } else if (data[0] != EOB) {
            if (getBits(1) != 0)
                data[1] = -data[1];
        }
        return data;
    }
}


/**
 * Fast inverse two-dimensional discrete cosine transform algorithm
 * by Chen-Wang using 32 bit integer arithmetic (8 bit coefficients).
 */
class IDCT {
    /**
     * The basic DCT block is 8x8 samples
     */
    private final static int DCTSIZE = 8;

    /**
     * Integer arithmetic precision constants
     */
    private final static int PASS_BITS = 3;
    private final static int CONST_BITS = 11;

    /**
     * Precomputed DCT cosine kernel functions:
     * Ci = (2^CONST_BITS)*sqrt(2.0)*cos(i * PI / 16.0)
     */
    private final static int C1 = 2841;
    private final static int C2 = 2676;
    private final static int C3 = 2408;
    private final static int C5 = 1609;
    private final static int C6 = 1108;
    private final static int C7 = 565;

    public static void transform(int block[]) {
    /* pass 1: process rows */
        for (int i = 0, offset = 0; i < DCTSIZE; i++, offset += DCTSIZE) {

      /* get coefficients */
            int d0 = block[offset + 0];
            int d4 = block[offset + 1];
            int d3 = block[offset + 2];
            int d7 = block[offset + 3];
            int d1 = block[offset + 4];
            int d6 = block[offset + 5];
            int d2 = block[offset + 6];
            int d5 = block[offset + 7];
            int d8;

      /* AC terms all zero? */
            if ((d1 | d2 | d3 | d4 | d5 | d6 | d7) == 0) {
                d0 <<= PASS_BITS;
                block[offset + 0] = d0;
                block[offset + 1] = d0;
                block[offset + 2] = d0;
                block[offset + 3] = d0;
                block[offset + 4] = d0;
                block[offset + 5] = d0;
                block[offset + 6] = d0;
                block[offset + 7] = d0;
                continue;
            }

      /* first stage */
            d8 = (d4 + d5) * C7;
            d4 = d8 + d4 * (C1 - C7);
            d5 = d8 - d5 * (C1 + C7);
            d8 = (d6 + d7) * C3;
            d6 = d8 - d6 * (C3 - C5);
            d7 = d8 - d7 * (C3 + C5);

      /* second stage */
            d8 = ((d0 + d1) << CONST_BITS) + (1 << (CONST_BITS - PASS_BITS - 1));
            d0 = ((d0 - d1) << CONST_BITS) + (1 << (CONST_BITS - PASS_BITS - 1));
            d1 = (d2 + d3) * C6;
            d2 = d1 - d2 * (C2 + C6);
            d3 = d1 + d3 * (C2 - C6);
            d1 = d4 + d6;
            d4 = d4 - d6;
            d6 = d5 + d7;
            d5 = d5 - d7;

      /* third stage */
            d7 = d8 + d3;
            d8 = d8 - d3;
            d3 = d0 + d2;
            d0 = d0 - d2;
            d2 = ((d4 + d5) * 181) >> 8;
            d4 = ((d4 - d5) * 181) >> 8;

      /* output stage */
            block[offset + 0] = (d7 + d1) >> (CONST_BITS - PASS_BITS);
            block[offset + 7] = (d7 - d1) >> (CONST_BITS - PASS_BITS);
            block[offset + 1] = (d3 + d2) >> (CONST_BITS - PASS_BITS);
            block[offset + 6] = (d3 - d2) >> (CONST_BITS - PASS_BITS);
            block[offset + 2] = (d0 + d4) >> (CONST_BITS - PASS_BITS);
            block[offset + 5] = (d0 - d4) >> (CONST_BITS - PASS_BITS);
            block[offset + 3] = (d8 + d6) >> (CONST_BITS - PASS_BITS);
            block[offset + 4] = (d8 - d6) >> (CONST_BITS - PASS_BITS);
        }

    /* pass 2: process columns */
        for (int i = 0, offset = 0; i < DCTSIZE; i++, offset++) {

      /* get coefficients */
            int d0 = block[offset + DCTSIZE * 0];
            int d4 = block[offset + DCTSIZE * 1];
            int d3 = block[offset + DCTSIZE * 2];
            int d7 = block[offset + DCTSIZE * 3];
            int d1 = block[offset + DCTSIZE * 4];
            int d6 = block[offset + DCTSIZE * 5];
            int d2 = block[offset + DCTSIZE * 6];
            int d5 = block[offset + DCTSIZE * 7];
            int d8;

      /* AC terms all zero? */
            if ((d1 | d2 | d3 | d4 | d5 | d6 | d7) == 0) {
                d0 >>= PASS_BITS + 3;
                block[offset + DCTSIZE * 0] = d0;
                block[offset + DCTSIZE * 1] = d0;
                block[offset + DCTSIZE * 2] = d0;
                block[offset + DCTSIZE * 3] = d0;
                block[offset + DCTSIZE * 4] = d0;
                block[offset + DCTSIZE * 5] = d0;
                block[offset + DCTSIZE * 6] = d0;
                block[offset + DCTSIZE * 7] = d0;
                continue;
            }

      /* first stage */
            d8 = (d4 + d5) * C7;
            d4 = (d8 + d4 * (C1 - C7)) >> 3;
            d5 = (d8 - d5 * (C1 + C7)) >> 3;
            d8 = (d6 + d7) * C3;
            d6 = (d8 - d6 * (C3 - C5)) >> 3;
            d7 = (d8 - d7 * (C3 + C5)) >> 3;

      /* second stage */
            d8 = ((d0 + d1) << (CONST_BITS - 3)) + (1 << (CONST_BITS + PASS_BITS - 1));
            d0 = ((d0 - d1) << (CONST_BITS - 3)) + (1 << (CONST_BITS + PASS_BITS - 1));
            d1 = (d2 + d3) * C6;
            d2 = (d1 - d2 * (C2 + C6)) >> 3;
            d3 = (d1 + d3 * (C2 - C6)) >> 3;
            d1 = d4 + d6;
            d4 = d4 - d6;
            d6 = d5 + d7;
            d5 = d5 - d7;

      /* third stage */
            d7 = d8 + d3;
            d8 = d8 - d3;
            d3 = d0 + d2;
            d0 = d0 - d2;
            d2 = ((d4 + d5) * 181) >> 8;
            d4 = ((d4 - d5) * 181) >> 8;

      /* output stage */
            block[offset + DCTSIZE * 0] = (d7 + d1) >> (CONST_BITS + PASS_BITS);
            block[offset + DCTSIZE * 7] = (d7 - d1) >> (CONST_BITS + PASS_BITS);
            block[offset + DCTSIZE * 1] = (d3 + d2) >> (CONST_BITS + PASS_BITS);
            block[offset + DCTSIZE * 6] = (d3 - d2) >> (CONST_BITS + PASS_BITS);
            block[offset + DCTSIZE * 2] = (d0 + d4) >> (CONST_BITS + PASS_BITS);
            block[offset + DCTSIZE * 5] = (d0 - d4) >> (CONST_BITS + PASS_BITS);
            block[offset + DCTSIZE * 3] = (d8 + d6) >> (CONST_BITS + PASS_BITS);
            block[offset + DCTSIZE * 4] = (d8 - d6) >> (CONST_BITS + PASS_BITS);
        }
    }
}


/**
 * Motion vector information used for MPEG-1 motion prediction.
 */
class MotionVector {
    /**
     * Motion vector displacements (6 bits)
     */
    public int horizontal;
    public int vertical;

    /**
     * Motion vector displacement residual size
     */
    private int residualSize;

    /**
     * Motion displacement in half or full pixels?
     */
    private boolean pixelSteps;


    /**
     * Initializes the motion vector object
     */
    public MotionVector() {
        horizontal = vertical = 0;
        residualSize = 0;
        pixelSteps = false;
    }

    /**
     * Changes the current motion vector predictors
     */
    public void setVector(int horizontal, int vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    /**
     * Reads the picture motion vector information
     */
    public void getMotionInfo(BitInputStream stream) throws IOException {
        pixelSteps = (stream.getBits(1) != 0);
        residualSize = (stream.getBits(3) - 1);
    }

    /**
     * Reads the macro block motion vectors
     */
    public void getMotionVector(VLCInputStream stream) throws IOException {
        horizontal = getMotionDisplacement(stream, horizontal);
        vertical = getMotionDisplacement(stream, vertical);
    }

    /**
     * Reads and reconstructs the motion vector displacements
     */
    private int getMotionDisplacement(VLCInputStream stream, int vector)
            throws IOException {
        int code = stream.getMVCode();
        int residual = (code != 0 && residualSize != 0 ?
                stream.getBits(residualSize) : 0);
        int limit = 16 << residualSize;
        if (pixelSteps)
            vector >>= 1;
        if (code > 0) {
            if ((vector += ((code - 1) << residualSize) + residual + 1) >= limit)
                vector -= limit << 1;
        } else if (code < 0) {
            if ((vector -= ((-code - 1) << residualSize) + residual + 1) < -limit)
                vector += limit << 1;
        }
        if (pixelSteps)
            vector <<= 1;
        return vector;
    }
}


/**
 * Macroblock decoder and dequantizer for MPEG-1 video streams.
 */
class Macroblock {
    /**
     * Macroblock type encoding
     */
    public final static int I_TYPE = 1;
    public final static int P_TYPE = 2;
    public final static int B_TYPE = 3;
    public final static int D_TYPE = 4;

    /**
     * Macroblock type bit fields
     */
    public final static int INTRA = 0x01;
    public final static int PATTERN = 0x02;
    public final static int BACKWARD = 0x04;
    public final static int FORWARD = 0x08;
    public final static int QUANT = 0x10;
    public final static int EMPTY = 0x80;

    /**
     * Default quantization matrix for intra coded macro blocks
     */
    private final static int defaultIntraMatrix[] = {
            8, 16, 16, 19, 16, 19, 22, 22,
            22, 22, 22, 22, 26, 24, 26, 27,
            27, 27, 26, 26, 26, 26, 27, 27,
            27, 29, 29, 29, 34, 34, 34, 29,
            29, 29, 27, 27, 29, 29, 32, 32,
            34, 34, 37, 38, 37, 35, 35, 34,
            35, 38, 38, 40, 40, 40, 48, 48,
            46, 46, 56, 56, 58, 69, 69, 83
    };

    /**
     * Mapping for zig-zag scan ordering
     */
    private final static int zigzag[] = {
            0, 1, 8, 16, 9, 2, 3, 10,
            17, 24, 32, 25, 18, 11, 4, 5,
            12, 19, 26, 33, 40, 48, 41, 34,
            27, 20, 13, 6, 7, 14, 21, 28,
            35, 42, 49, 56, 57, 50, 43, 36,
            29, 22, 15, 23, 30, 37, 44, 51,
            58, 59, 52, 45, 38, 31, 39, 46,
            53, 60, 61, 54, 47, 55, 62, 63
    };

    /**
     * Quantization scale for macro blocks
     */
    private int scale;

    /**
     * Quantization matrix for intra coded blocks
     */
    private int intraMatrix[];

    /**
     * Quantization matrix for inter coded blocks
     */
    private int interMatrix[];

    /**
     * Color components sample blocks (8 bits)
     */
    private int block[][];

    /**
     * Predictors for DC coefficients (10 bits)
     */
    private int predictor[];

    /**
     * Macroblock type encoding
     */
    private int type;

    /**
     * Macroblock type flags
     */
    private int flags;

    /**
     * Motion prediction vectors
     */
    private MotionVector forward;
    private MotionVector backward;


    /**
     * Initializes the MPEG-1 macroblock decoder object
     */
    public Macroblock() {
    /* create quantization matrices */
        intraMatrix = new int[64];
        interMatrix = new int[64];

    /* create motion prediction vectors */
        forward = new MotionVector();
        backward = new MotionVector();

    /* create DCT blocks and predictors */
        block = new int[6][64];
        predictor = new int[3];

    /* set up default macro block types */
        type = I_TYPE;
        flags = EMPTY;

    /* set up default quantization scale */
        scale = 0;

    /* set up default quantization matrices */
        for (int i = 0; i < 64; i++) {
            intraMatrix[i] = defaultIntraMatrix[i];
            interMatrix[i] = 16;
        }

    /* set up default DC coefficient predictors */
        for (int i = 0; i < 3; i++)
            predictor[i] = 1024;
    }

    /**
     * Returns the quantization scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * Changes the quantization scale
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * Returns the quantization matrix for intra coded blocks
     */
    public int[] getIntraMatrix() {
        return intraMatrix;
    }

    /**
     * Changes the quantization matrix for intra coded blocks
     */
    public void setIntraMatrix(int matrix[]) {
        for (int i = 0; i < 64; i++)
            intraMatrix[i] = matrix[i];
    }

    /**
     * Returns the quantization matrix for inter coded blocks
     */
    public int[] getInterMatrix() {
        return interMatrix;
    }

    /**
     * Changes the quantization matrix for inter coded blocks
     */
    public void setInterMatrix(int matrix[]) {
        for (int i = 0; i < 64; i++)
            interMatrix[i] = matrix[i];
    }

    /**
     * Returns the macroblock type encoding
     */
    public int getType() {
        return type;
    }

    /**
     * Changes the macroblock type encoding
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns the component block of samples
     */
    public int[][] getData() {
        return block;
    }

    /**
     * Changes the component block of samples
     */
    public void setData(int component, int data[]) {
        for (int i = 0; i < 64; i++)
            block[component][i] = data[i];
    }

    /**
     * Returns the macro block type flags
     */
    public int getFlags() {
        return flags;
    }

    /**
     * Changes the macro block type flags
     */
    public void setFlags(int flags) {
        this.flags = flags;
    }

    /**
     * Returns true if the block is empty
     */
    public boolean isEmpty() {
        return (flags & EMPTY) != 0;
    }

    /**
     * Returns true if the block is intra coded
     */
    public boolean isIntraCoded() {
        return (flags & INTRA) != 0;
    }

    /**
     * Returns true if the block is pattern coded
     */
    public boolean isPatternCoded() {
        return ((flags & PATTERN) != 0);
    }

    /**
     * Returns true if the block is forward predicted
     */
    public boolean isBackwardPredicted() {
        return (flags & BACKWARD) != 0;
    }

    /**
     * Returns true if the block is backward predicted
     */
    public boolean isForwardPredicted() {
        return (flags & FORWARD) != 0;
    }

    /**
     * Returns true if the block is forward and backward predicted
     */
    public boolean isBidirPredicted() {
        return ((flags & FORWARD) != 0) && ((flags & BACKWARD) != 0);
    }

    /**
     * Returns true if the block has a quantization scale
     */
    public boolean isQuantScaled() {
        return ((flags & QUANT) != 0);
    }

    /**
     * Returns the forward motion vector
     */
    public MotionVector getForwardVector() {
        return forward;
    }

    /**
     * Returns the backward motion vector
     */
    public MotionVector getBackwardVector() {
        return backward;
    }

    /**
     * Resets the DCT coefficient predictors
     */
    public void resetDataPredictors() {
        for (int i = 0; i < 3; i++)
            predictor[i] = 1024;
    }

    /**
     * Resets the motion vector predictors
     */
    public void resetMotionVectors() {
        forward.setVector(0, 0);
        backward.setVector(0, 0);
    }

    /**
     * Parses the next encoded MPEG-1 macroblock (according to ISO 11172-2)
     * decoding and dequantizing the DCT coefficient component blocks.
     */
    public void getMacroblock(VLCInputStream stream) throws IOException {
    /* read macro block bit flags */
        switch (getType()) {
            case I_TYPE:
                setFlags(stream.getIMBCode());
                break;
            case P_TYPE:
                setFlags(stream.getPMBCode());
                if (!isForwardPredicted())
                    resetMotionVectors();
                if (!isIntraCoded())
                    resetDataPredictors();
                break;
            case B_TYPE:
                setFlags(stream.getBMBCode());
                if (isIntraCoded())
                    resetMotionVectors();
                else
                    resetDataPredictors();
                break;
        }

    /* read quantization scale */
        if (isQuantScaled()) {
            setScale(stream.getBits(5));
        }

    /* read forward motion vector */
        if (isForwardPredicted()) {
            getForwardVector().getMotionVector(stream);
        }

    /* read backward motion vector */
        if (isBackwardPredicted()) {
            getBackwardVector().getMotionVector(stream);
        }

    /* read block pattern code */
        int pattern = 0;
        if (isPatternCoded()) {
            pattern = stream.getCBPCode();
        }

    /* clear DCT coefficients blocks */
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 64; j++) {
                block[i][j] = 0;
            }
        }

    /* read DCT coefficient blocks */
        if (isIntraCoded()) {
      /* read intra coded DCT coefficients */
            for (int i = 0; i < 6; i++) {
                block[i][0] = predictor[i < 4 ? 0 : i - 3];
                getIntraBlock(stream, block[i], i);
                predictor[i < 4 ? 0 : i - 3] = block[i][0];
                IDCT.transform(block[i]);
            }
        } else {
      /* read inter coded DCT coefficients */
            for (int i = 0; i < 6; i++) {
                if ((pattern & (1 << (5 - i))) != 0) {
                    getInterBlock(stream, block[i]);
                    IDCT.transform(block[i]);
                }
            }
        }
    }

    /**
     * Parses an intra coded MPEG-1 block (according to ISO 11172-2) decoding
     * and dequantizing the DC and AC coefficients stored in zig zag order.
     */
    private void getIntraBlock(VLCInputStream stream, int block[], int component)
            throws IOException {
    /* decode DC coefficients */
        if (component < 4)
            block[0] += stream.getIntraDCLumValue() << 3;
        else
            block[0] += stream.getIntraDCChromValue() << 3;
    /* decode AC coefficients */
        for (int i = 1; i <= block.length; i++) {
            int data[] = stream.getACValue();
            if (data[0] == stream.EOB)
                break;
            int position = zigzag[i = (i + data[0]) & 63];
            block[position] = (data[1] * scale * intraMatrix[i]) >> 3;
        }
    }

    /**
     * Parses a inter coded MPEG-1 block (according to ISO 11172-2) decoding
     * and dequantizing the DC and AC coefficients stored in zig zag order.
     */
    private void getInterBlock(VLCInputStream stream, int block[])
            throws IOException {
    /* decode DC and AC coefficients */
        for (int i = 0; i <= block.length; i++) {
            int data[] = (i == 0 ? stream.getInterDCValue() : stream.getACValue());
            if (data[0] == stream.EOB)
                break;
            data[1] += ((data[1] >> 31) << 1) + 1;
            int position = zigzag[i = (i + data[0]) & 63];
            block[position] = (data[1] * scale * interMatrix[i]) >> 3;
        }
    }
}


/**
 * Picture decoder for MPEG-1 video streams according to ISO 11172-2.
 */
class Picture {
    /**
     * Picture current and predictors frame buffers
     */
    private int frameBuffer[];
    private int forwardBuffer[];
    private int backwardBuffer[];

    /**
     * Macroblock decoder and dequantizer
     */
    private Macroblock macroblock;

    /**
     * Dimension of the picture in pixels
     */
    private int width, height;

    /**
     * Dimension of the picture in macro blocks
     */
    private int mbColumns, mbRows;

    /**
     * Picture temporal reference number
     */
    private int number;

    /**
     * Picture synchronization delay (1/90000s ticks)
     */
    private int delay;


    /**
     * Constructs a MPEG-1 video stream picture
     */
    public Picture() {
    /* constructs the macroblock */
        macroblock = new Macroblock();

    /* initialize temporal fields */
        number = 0;
        delay = 0;

    /* initialize dimension and frame buffers */
        width = height = 0;
        mbColumns = mbRows = 0;
        frameBuffer = null;
        forwardBuffer = null;
        backwardBuffer = null;
    }

    /**
     * Changes the picture dimensions
     */
    public void setSize(int width, int height) {
    /* set up picture dimension */
        this.width = width;
        this.height = height;

    /* compute dimension in macro blocks */
        mbColumns = (width + 15) >> 4;
        mbRows = (height + 15) >> 4;

    /* allocate frame buffers */
        frameBuffer = new int[256 * mbRows * mbColumns];
        forwardBuffer = new int[256 * mbRows * mbColumns];
        backwardBuffer = new int[256 * mbRows * mbColumns];
    }

    /**
     * Returns the picture temporal reference
     */
    public int getNumber() {
        return number;
    }

    /**
     * Changes the picture temporal reference
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Returns the picture temporal delay
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Changes the picture temporal delay
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * Returns the macro block object
     */
    public Macroblock getMacroblock() {
        return macroblock;
    }

    /**
     * Returns the dimension of the picture in pixels
     */
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStride() {
        return mbColumns << 4;
    }

    /**
     * Returns the last picture of the MPEG-1 video stream
     */
    public int[] getLastFrame() {
        return backwardBuffer;
    }

    /**
     * Parses the next picture from the MPEG-1 video stream
     */
    public int[] getFrame(VLCInputStream stream) throws IOException {
    /* read picture temporal reference */
        setNumber(stream.getBits(10));

    /* read picture encoding type */
        macroblock.setType(stream.getBits(3));

    /* read VBV delay of this picture */
        setDelay(stream.getBits(16));

    /* read forward motion information */
        if (macroblock.getType() != Macroblock.I_TYPE) {
            macroblock.getForwardVector().getMotionInfo(stream);
        }

    /* read backward motion information */
        if (macroblock.getType() == Macroblock.B_TYPE) {
            macroblock.getBackwardVector().getMotionInfo(stream);
        }

    /* skip extra bit information */
        while (stream.getBits(1) != 0)
            stream.flushBits(8);

    /* skip extensions and user data chunks */
        while (stream.showCode() == BitInputStream.EXT_START_CODE ||
                stream.showCode() == BitInputStream.USER_START_CODE) {
            stream.getCode();
        }

    /* update forward frame buffer */
        if (macroblock.getType() != Macroblock.B_TYPE) {
            int buffer[] = forwardBuffer;
            forwardBuffer = backwardBuffer;
            backwardBuffer = buffer;
        }

    /* parse picture slices */
        while (stream.showCode() >= BitInputStream.SLICE_MIN_CODE &&
                stream.showCode() <= BitInputStream.SLICE_MAX_CODE) {
            getSlice(stream, stream.getCode());
        }

    /* update backward frame buffer */
        if (macroblock.getType() != Macroblock.B_TYPE) {
            int buffer[] = backwardBuffer;
            backwardBuffer = frameBuffer;
            frameBuffer = buffer;
            return forwardBuffer;
        }

        return frameBuffer;
    }

    /**
     * Parses the next picture slice from the MPEG-1 video stream
     */
    private void getSlice(VLCInputStream stream, int code) throws IOException {
    /* compute macro block address */
        int address = (code - BitInputStream.SLICE_MIN_CODE) * mbColumns - 1;

    /* read slice quantization scale */
        macroblock.setScale(stream.getBits(5));

    /* skip extra bit information */
        while (stream.getBits(1) != 0) {
            stream.flushBits(8);
        }

    /* reset DCT predictors and motion vectors */
        macroblock.setFlags(Macroblock.EMPTY);
        macroblock.resetDataPredictors();
        macroblock.resetMotionVectors();

    /* parse slice macro blocks */
        while (stream.showBits(23) != 0) {
      /* get macro block address increment */
            int lastAddress = address + stream.getMBACode();

      /* handle skipped macro blocks */
            if (macroblock.isEmpty()) {
    /* handle the first macro block address in the slice */
                address = lastAddress;
            } else {
                while (++address < lastAddress) {
      /* assume inter coded macro block with zero coefficients */
                    macroblock.resetDataPredictors();

	  /* use previous motion vectors or zero in P-picture macro blocks */
                    if (macroblock.getType() == Macroblock.P_TYPE)
                        macroblock.resetMotionVectors();

	  /* process skipped macro block */
                    if (macroblock.isBidirPredicted()) {
                        motionPrediction(address, forwardBuffer, backwardBuffer,
                                macroblock.getForwardVector(),
                                macroblock.getBackwardVector());
                    } else if (macroblock.isBackwardPredicted()) {
                        motionPrediction(address, backwardBuffer,
                                macroblock.getBackwardVector());
                    } else {
                        motionPrediction(address, forwardBuffer,
                                macroblock.getForwardVector());
                    }
                }
            }

      /* decode macro block */
            macroblock.getMacroblock(stream);

      /* process macro block */
            if (macroblock.isIntraCoded()) {
                motionPrediction(address, macroblock.getData());
            } else {
                if (macroblock.isBidirPredicted()) {
                    motionPrediction(address, forwardBuffer, backwardBuffer,
                            macroblock.getForwardVector(),
                            macroblock.getBackwardVector());
                } else if (macroblock.isBackwardPredicted()) {
                    motionPrediction(address, backwardBuffer,
                            macroblock.getBackwardVector());
                } else {
                    motionPrediction(address, forwardBuffer,
                            macroblock.getForwardVector());
                }
                motionCompensation(address, macroblock.getData());
            }
        }
    }

    private void motionPrediction(int address, int sourceBuffer[],
                                  MotionVector vector) {
        int width = mbColumns << 4;
        int offset = ((address % mbColumns) + width * (address / mbColumns)) << 4;
        int deltaA = (vector.horizontal >> 1) + width * (vector.vertical >> 1);
        int deltaB = (vector.horizontal & 1) + width * (vector.vertical & 1);
        if (deltaB == 0) {
            for (int i = 0; i < 16; i++) {
                System.arraycopy(sourceBuffer, offset + deltaA,
                        frameBuffer, offset, 16);
                offset += width;
            }
        } else {
            deltaB += deltaA;
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    int d0 = sourceBuffer[offset + deltaA];
                    int d1 = sourceBuffer[offset + deltaB];
                    int d2 = (d0 & 0xfefefe) + (d1 & 0xfefefe);
                    int d3 = (d0 & d1) & 0x010101;
                    frameBuffer[offset++] = (d2 >> 1) + d3;
                }
                offset += width - 16;
            }
        }
    }

    private void motionPrediction(int address,
                                  int sourceBufferA[], int sourceBufferB[],
                                  MotionVector vectorA, MotionVector vectorB) {
        int width = mbColumns << 4;
        int offset = ((address % mbColumns) + width * (address / mbColumns)) << 4;
        int deltaA = (vectorA.horizontal >> 1) + width * (vectorA.vertical >> 1);
        int deltaB = (vectorB.horizontal >> 1) + width * (vectorB.vertical >> 1);
        int deltaC = (vectorA.horizontal & 1) + width * (vectorA.vertical & 1);
        int deltaD = (vectorB.horizontal & 1) + width * (vectorB.vertical & 1);
        if (deltaC == 0 && deltaD == 0) {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    int d0 = sourceBufferA[offset + deltaA];
                    int d1 = sourceBufferB[offset + deltaB];
                    int d2 = (d0 & 0xfefefe) + (d1 & 0xfefefe);
                    int d3 = (d0 & d1) & 0x010101;
                    frameBuffer[offset++] = (d2 >> 1) + d3;
                }
                offset += width - 16;
            }
        } else {
            deltaC += deltaA;
            deltaD += deltaB;
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    int d0 = sourceBufferA[offset + deltaA];
                    int d1 = sourceBufferB[offset + deltaB];
                    int d2 = sourceBufferA[offset + deltaC];
                    int d3 = sourceBufferB[offset + deltaD];
                    int d4 = ((d0 & 0xfcfcfc) + (d1 & 0xfcfcfc) +
                            (d2 & 0xfcfcfc) + (d3 & 0xfcfcfc));
                    int d5 = (d0 + d1 + d2 + d3 - d4) & 0x040404;
                    frameBuffer[offset++] = (d4 + d5) >> 2;
                }
                offset += width - 16;
            }
        }
    }

    private void motionPrediction(int address, int block[][]) {
        int width, offset, index;

    /* compute macroblock address */
        width = mbColumns << 4;
        address = ((address % mbColumns) + width * (address / mbColumns)) << 4;

    /* reconstruct luminance blocks */
        offset = address;
        index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                frameBuffer[offset] = clip[512 + block[0][index]];
                frameBuffer[offset + 8] = clip[512 + block[1][index]];
                offset++;
                index++;
            }
            offset += width - 8;
        }
        offset = address + (width << 3);
        index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                frameBuffer[offset] = clip[512 + block[2][index]];
                frameBuffer[offset + 8] = clip[512 + block[3][index]];
                offset++;
                index++;
            }
            offset += width - 8;
        }

    /* reconstruct chrominance blocks */
        offset = address;
        index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int Cb = clip[512 + block[4][index]];
                int Cr = clip[512 + block[5][index]];
                int CbCr = (Cb << 8) + (Cr << 16);

                frameBuffer[offset++] += CbCr;
                frameBuffer[offset++] += CbCr;

                offset += width - 2;

                frameBuffer[offset++] += CbCr;
                frameBuffer[offset++] += CbCr;

                offset -= width;
                index++;
            }
            offset += width + width - 16;
        }
    }

    private void motionCompensation(int address, int block[][]) {
        int width, offset, index;

    /* compute macroblock address */
        width = mbColumns << 4;
        address = ((address % mbColumns) + width * (address / mbColumns)) << 4;

    /* reconstruct luminance blocks */
        offset = index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                data[offset] = block[0][index];
                data[offset + 8] = block[1][index];
                data[offset + 128] = block[2][index];
                data[offset + 8 + 128] = block[3][index];
                offset++;
                index++;
            }
            offset += 8;
        }

    /* reconstruct chrominance blocks */
        offset = index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int Y, YCbCr;
                int Cb = 512 + block[4][index];
                int Cr = 512 + block[5][index];

                Y = 512 + data[offset++];
                YCbCr = frameBuffer[address];
                frameBuffer[address++] =
                        (clip[((YCbCr >> 0) & 0xFF) + Y] << 0) +
                                (clip[((YCbCr >> 8) & 0xFF) + Cb] << 8) +
                                (clip[((YCbCr >> 16) & 0xFF) + Cr] << 16);

                Y = 512 + data[offset++];
                YCbCr = frameBuffer[address];
                frameBuffer[address++] =
                        (clip[((YCbCr >> 0) & 0xFF) + Y] << 0) +
                                (clip[((YCbCr >> 8) & 0xFF) + Cb] << 8) +
                                (clip[((YCbCr >> 16) & 0xFF) + Cr] << 16);

                offset += 16 - 2;
                address += width - 2;

                Y = 512 + data[offset++];
                YCbCr = frameBuffer[address];
                frameBuffer[address++] =
                        (clip[((YCbCr >> 0) & 0xFF) + Y] << 0) +
                                (clip[((YCbCr >> 8) & 0xFF) + Cb] << 8) +
                                (clip[((YCbCr >> 16) & 0xFF) + Cr] << 16);

                Y = 512 + data[offset++];
                YCbCr = frameBuffer[address];
                frameBuffer[address++] =
                        (clip[((YCbCr >> 0) & 0xFF) + Y] << 0) +
                                (clip[((YCbCr >> 8) & 0xFF) + Cb] << 8) +
                                (clip[((YCbCr >> 16) & 0xFF) + Cr] << 16);

                offset -= 16;
                address -= width;
                index++;
            }
            offset += 16;
            address += width + width - 16;
        }
    }

    private static int data[] = new int[256];
    private static int clip[] = new int[1024];

    static {
        for (int i = 0; i < 1024; i++) {
            clip[i] = Math.min(Math.max(i - 512, 0), 255);
        }
    }
}


/**
 * The MPEG-1 video stream decoder according to ISO 11172-2.
 */
class MPEGVideoStream {
    /**
     * MPEG-1 video frame rate table
     */
    private static final int frameRateTable[] = {
            30, 24, 24, 25, 30, 30, 50, 60, 60, 12, 30, 30, 30, 30, 30, 30
    };

    /**
     * MPEG-1 video stream frame rate (frames per second)
     */
    private int frameRate;

    /**
     * MPEG-1 video stream bit rate (bits per second)
     */
    private int bitRate;

    /**
     * MPEG-1 video stream VBV buffer size (16 kbit steps)
     */
    private int bufferSize;

    /**
     * MPEG-1 video stream time record (in frames)
     */
    private int hour, minute, second, frame;

    /**
     * MPEG-1 video stream current picture
     */
    private Picture picture;

    /**
     * MPEG-1 video stream boolean fields
     */
    private boolean constrained, dropped, closed, broken;

    /**
     * The last MPEG-1 picture frame parsed
     */
    private int frameBuffer[];

    /**
     * The underlaying VLC input stream
     */
    private VLCInputStream stream;

    /**
     * Initializes the MPEG-1 video input stream object
     */
    public MPEGVideoStream(InputStream inputStream) throws IOException {
    /* set ups the VLC input stream */
        stream = new VLCInputStream(inputStream);

    /* set ups the picture decoder */
        picture = new Picture();

    /* reset rates and buffer size */
        frameRate = 0;
        bitRate = 0;
        bufferSize = 0;

    /* reset time record */
        hour = 0;
        minute = 0;
        second = 0;
        frame = 0;

    /* reset boolean fields */
        constrained = false;
        dropped = false;
        closed = false;
        broken = false;

    /* reset last frame buffer */
        frameBuffer = getFrame();
    }

    /**
     * Returns the MPEG-1 video stream frame rate
     */
    public int getFrameRate() {
        return frameRate;
    }

    /**
     * Changes the MPEG-1 video stream frame rate
     */
    public void setFrameRate(int rate) {
        frameRate = rate;
    }

    /**
     * Returns the MPEG-1 video stream bit rate
     */
    public int getBitRate() {
        return bitRate;
    }

    /**
     * Changes the MPEG-1 video stream bit rate
     */
    public void setBitRate(int rate) {
        bitRate = rate;
    }

    /**
     * Returns the MPEG-1 video stream VBV buffer size
     */
    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * Changes the MPEG-1 video stream VBV buffer size
     */
    public void setBufferSize(int size) {
        bufferSize = size;
    }

    /**
     * Returns the MPEG-1 video stream time record
     */
    public long getTime() {
        return frame + getFrameRate() * (second + 60L * minute + 3600L * hour);
    }

    /**
     * Changes the MPEG-1 video stream time record
     */
    public void setTime(int hour, int minute, int second, int frame) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.frame = frame;
    }

    /**
     * Returns true if the video parameters are constrained
     */
    public boolean isConstrained() {
        return constrained;
    }

    /**
     * Enables or disables the video parameters constrains
     */
    public void setConstrained(boolean constrained) {
        this.constrained = constrained;
    }

    /**
     * Returns true if the group of pictures drops frames
     */
    public boolean isDropped() {
        return dropped;
    }

    /**
     * Changes the dropped flag of the group of pictures
     */
    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }

    /**
     * Returns true if the group of pictures is closed
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Changes the closed flag of the group of pictures
     */
    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    /**
     * Returns true if there is a broken link
     */
    public boolean isBroken() {
        return broken;
    }

    /**
     * Changes the broken flag of the group of pictures
     */
    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    /**
     * Returns the MPEG-1 video picture dimensions
     */
    public int getWidth() {
        return picture.getWidth();
    }

    public int getHeight() {
        return picture.getHeight();
    }

    public int getStride() {
        return picture.getStride();
    }

    /**
     * Parses the next frame of the MPEG-1 video stream
     */
    public int[] getFrame() throws IOException {
        while (stream.showCode() != BitInputStream.SEQ_END_CODE) {
            switch (stream.getCode()) {
                case BitInputStream.SEQ_START_CODE:
                    getSequenceHeader(stream);
                    break;
                case BitInputStream.GOP_START_CODE:
                    getGroupPictures(stream);
                    break;
                case BitInputStream.PIC_START_CODE:
                    return getPictureFrame(stream);
                case BitInputStream.USER_START_CODE:
                case BitInputStream.EXT_START_CODE:
                    break;
                default:
                    // throw new IOException("Unknown MPEG-1 video layer start code");
                    break;
            }
        }
        if (frameBuffer != picture.getLastFrame()) {
            frameBuffer = picture.getLastFrame();
            return frameBuffer;
        }
        return null;
    }

    /**
     * Parses the sequence header from the MPEG-1 video stream
     */
    private void getSequenceHeader(VLCInputStream stream) throws IOException {
    /* read picture dimensions in pixels */
        int width = stream.getBits(12);
        int height = stream.getBits(12);
        int aspectRatio = stream.getBits(4);

    /* changes the MPEG-1 picture dimension */
        if (picture.getWidth() == 0 && picture.getHeight() == 0)
            picture.setSize(width, height);

    /* read picture and bit rates */
        setFrameRate(frameRateTable[stream.getBits(4)]);
        setBitRate(400 * stream.getBits(18));
        stream.getBits(1);

    /* read VBV buffer size */
        setBufferSize(stream.getBits(10));

    /* read constrained parameters flag */
        setConstrained(stream.getBits(1) != 0);

    /* read quantization matrix for intra coded blocks */
        int intraMatrix[] = picture.getMacroblock().getIntraMatrix();
        if (stream.getBits(1) != 0) {
            for (int i = 0; i < 64; i++)
                intraMatrix[i] = stream.getBits(8);
        }

    /* read quantization matrix for inter coded blocks */
        int interMatrix[] = picture.getMacroblock().getInterMatrix();
        if (stream.getBits(1) != 0) {
            for (int i = 0; i < 64; i++)
                interMatrix[i] = stream.getBits(8);
        }
    }

    /**
     * Parses group of pictures header from the MPEG-1 video stream
     */
    private void getGroupPictures(VLCInputStream stream) throws IOException {
    /* read the drop frame flag */
        setDropped(stream.getBits(1) != 0);

    /* read the time record */
        int hour = stream.getBits(5);
        int minute = stream.getBits(6);
        int marker = stream.getBits(1);
        int second = stream.getBits(6);
        int frame = stream.getBits(6);
        setTime(hour, minute, second, frame);

    /* read closed and broken link flags */
        setClosed(stream.getBits(1) != 0);
        setBroken(stream.getBits(1) != 0);
    }

    /**
     * Parses the next picture from the MPEG-1 video stream
     */
    private int[] getPictureFrame(VLCInputStream stream) throws IOException {
        return picture.getFrame(stream);
    }
}


/**
 * The MPEG-1 video stream decoder applet that is intended to run
 * embedded inside of a Web page or another application.
 */
public class MPEGPlayer extends Applet implements Runnable {
    /**
     * The MPEG-1 video input stream
     */
    private MPEGVideoStream stream;

    /**
     * The picture frame buffer
     */
    private int pixels[], width, height, stride;

    /**
     * The memory image color model
     */
    private DirectColorModel model = null;

    /**
     * The memory image source
     */
    private MemoryImageSource source = null;

    /**
     * The memory image object
     */
    private Image image = null;

    /**
     * The applet's execution thread
     */
    private Thread kicker = null;

    /**
     * The video stream location
     */
    private URL url = null;

    /**
     * The repeat boolean parameter
     */
    private boolean repeat = true;


    /**
     * Applet information
     */
    public String getAppletInfo() {
        return "MPEGPlayer 0.9 (15 Apr 1998), Carlos Hasan (chasan@dcc.uchile.cl)";
    }

    /**
     * Parameters information
     */
    public String[][] getParameterInfo() {
        String info[][] = {
                {"source", "URL", "MPEG-1 video stream location"},
                {"repeat", "boolean", "repeat the video sequence"}};
        return info;
    }

    /**
     * Applet initialization
     */
    public void init() {
        try {
            if (getParameter("source") != null)
                url = new URL(getDocumentBase(), getParameter("source"));
            if (getParameter("repeat") != null)
                repeat = getParameter("repeat").equalsIgnoreCase("true");
        } catch (MalformedURLException exception) {
            showStatus("MPEG Exception: " + exception);
        }
    }

    /**
     * Start the execution of the applet
     */
    public void start() {
        if (kicker == null && url != null) {
            kicker = new Thread(this);
            kicker.start();
        }
        showStatus(getAppletInfo());
    }

    /**
     * Stop the execution of the applet
     */
    public void stop() {
        if (kicker != null && kicker.isAlive()) {
            kicker.stop();
        }
        kicker = null;
    }

    /**
     * The applet main execution code
     */
    public void run() {
        int frame[];
        long time;
        try {
            do {
                stream = new MPEGVideoStream(url.openStream());
                width = stream.getWidth();
                height = stream.getHeight();
                stride = stream.getStride();
                resize(width, height);
                pixels = new int[stride * height];
                model = new DirectColorModel(24, 0x0000ff, 0x00ff00, 0xff0000);
                time = System.currentTimeMillis();
                while ((frame = readFrame()) != null) {
                    drawFrame(frame, width, height, stride);
                    source = new MemoryImageSource(width, height, model, pixels, 0, stride);
                    image = createImage(source);
                    paint(getGraphics());
                    time += 1000L / stream.getFrameRate();
                    try {
                        Thread.sleep(Math.max(time - System.currentTimeMillis(), 0));
                    } catch (InterruptedException exception) {
                    }
                    image.flush();
                }
            } while (repeat);
        } catch (IOException exception) {
            showStatus("MPEG I/O Exception: " + exception);
        }
    }

    /**
     * Paint the current frame
     */
    public void paint(Graphics graphics) {
        if (image != null)
            graphics.drawImage(image, 0, 0, null);
    }

    /**
     * Reads the next MPEG-1 video frame
     */
    private int[] readFrame() {
        while (true) {
            try {
                return stream.getFrame();
            } catch (Exception exception) {
                showStatus("MPEG Exception: " + exception);
            }
        }
    }

    /**
     * Draws the current MPEG-1 video frame
     */
    private void drawFrame(int frame[], int width, int height, int stride) {
        int offset = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int YCbCr = frame[offset];
                int Y = 512 + ((YCbCr >> 0) & 0xff);
                int Cb = cbtable[(YCbCr >> 8) & 0xff];
                int Cr = crtable[(YCbCr >> 16) & 0xff];
                pixels[offset++] =
                        (clip[Y + (Cr >> 16)] << 0) +
                                (clip[Y + (((Cb + Cr) << 16) >> 16)] << 8) +
                                (clip[Y + (Cb >> 16)] << 16);
            }
            offset += stride - width;
        }
    }

    /**
     * Color conversion lookup tables
     */
    private static int clip[], cbtable[], crtable[];

    static {
        clip = new int[1024];
        cbtable = new int[256];
        crtable = new int[256];
        for (int i = 0; i < 1024; i++) {
            clip[i] = Math.min(Math.max(i - 512, 0), 255);
        }
        for (int i = 0; i < 256; i++) {
            int level = i - 128;
            cbtable[i] = (((int) (1.77200 * level)) << 16) - ((int) (0.34414 * level));
            crtable[i] = (((int) (1.40200 * level)) << 16) - ((int) (0.71414 * level));
        }
    }
}
