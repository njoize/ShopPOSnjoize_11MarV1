package njoize.dai_ka.com.demotestprint;

public class Command {

    public static final byte[] top = new byte[]{0x10, 0x04, 0x04}; // Space Front Bill
    public static final byte[] lineup = new byte[]{0x0A, 0x0D}; // Update Line
    public static final byte[] centered = new byte[]{0x1B, 0x61, 1}; // centered
    public static final byte[] left = new byte[]{0x1B, 0x61, 0}; // left
    public static final byte[] right = new byte[]{0x1B, 0x61, 2}; // right
    public static final byte[] tab = new byte[]{27, 101, 0, 9}; // tab
    public static final byte[] dfont = new byte[]{0x1B, 0x21, 0x00 }; // default font
    public static final byte[] bold = new byte[]{0x1B, 0x45, 0x01}; // bold
    public static final byte[] dbold = new byte[]{0x1B, 0x21, 0x04 | 0x08 | 0x20}; // Set the font (double height and width bold)

//    byte[] dbold = new byte[3]; // Set the font (double height and width bold)
//    dbold[0] = 0x1B;
//    dbold[1] = 0x21;
//    dbold[2] |= 0x04; // 08 04 bold
//    dbold[2] |= 0x08; // 10 08 height
//    dbold[2] |= 0x20; // 20 10

    public static final byte[] openCashDrawer = new byte[]{0x1B, 0x70, 0x00, 0x40, 0x50}; // Open Cash Drawer
    public static final byte[] cutterPaper = new byte[]{0x1D, 0x56, 0x42, 90}; // Cutter Paper command


}
