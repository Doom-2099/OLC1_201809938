// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: src/Context/Lex.flex

package Context;
import java_cup.runtime.Symbol;
import Error.ListError;
import Error.Error;


// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
public class Lex implements java_cup.runtime.Scanner {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0, 0
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\1\u0100\36\u0200\1\u0300\267\u0200\10\u0400\u1020\u0200";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\2\3\1\4\22\0\1\1\1\0"+
    "\1\5\1\0\1\6\2\0\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\12\20\1\0\1\21"+
    "\2\0\1\22\1\23\1\0\1\24\1\25\1\26\1\27"+
    "\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37"+
    "\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47"+
    "\1\50\1\51\2\36\1\52\1\36\4\0\1\53\1\0"+
    "\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33"+
    "\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43"+
    "\1\44\1\45\1\46\1\47\1\50\1\51\2\36\1\52"+
    "\1\36\1\54\1\0\1\55\7\0\1\3\71\0\1\56"+
    "\160\0\2\57\115\0\1\60\u019c\0\1\61\1\62\12\0"+
    "\2\3\326\0\u0100\3";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[1280];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\2\1\1\4\1\5\1\6"+
    "\1\7\1\10\1\11\1\12\1\13\1\14\1\15\20\1"+
    "\1\16\1\0\1\17\2\0\1\20\2\0\1\13\24\0"+
    "\1\21\5\0\1\22\3\0\1\23\3\0\2\2\1\24"+
    "\12\0\1\25\13\0\1\26\10\0\1\27\1\0\1\2"+
    "\3\0\1\30\24\0\1\31\1\32\5\0\1\27\13\0"+
    "\1\33\5\0\1\34\1\35\3\0\1\36\1\37\7\0"+
    "\1\40\2\0\1\41\16\0\1\42\4\0\1\43\2\0"+
    "\1\44\1\0\1\45\1\46\4\0\1\47\16\0\1\50"+
    "\7\0\1\51\2\0\1\52\4\0\1\53\1\54\1\0"+
    "\1\55\3\0\1\56\2\0\1\57\1\60\2\0\1\61"+
    "\1\62\1\63\3\0\1\64\5\0\1\65\1\66\3\0"+
    "\1\67\5\0\1\70\10\0\1\71\1\0\1\72\5\0"+
    "\1\73\1\74\5\0\1\75\1\76\1\0\1\77\1\0"+
    "\1\100\1\101";

  private static int [] zzUnpackAction() {
    int [] result = new int[308];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\63\0\146\0\231\0\314\0\377\0\63\0\63"+
    "\0\63\0\63\0\63\0\u0132\0\u0165\0\u0198\0\63\0\63"+
    "\0\u01cb\0\u01fe\0\u0231\0\u0264\0\u0297\0\u02ca\0\u02fd\0\u0330"+
    "\0\u0363\0\u0396\0\u03c9\0\u03fc\0\u042f\0\u0462\0\u0495\0\u04c8"+
    "\0\63\0\314\0\63\0\u04fb\0\u052e\0\63\0\u0561\0\u0594"+
    "\0\u05c7\0\u05fa\0\u062d\0\u0660\0\u0693\0\u06c6\0\u06f9\0\u072c"+
    "\0\u075f\0\u0792\0\u07c5\0\u07f8\0\u082b\0\u085e\0\u0891\0\u08c4"+
    "\0\u08f7\0\u092a\0\u095d\0\u0990\0\u09c3\0\63\0\u09f6\0\u0a29"+
    "\0\u0a5c\0\u0a8f\0\u0ac2\0\63\0\u0af5\0\u0b28\0\u0b5b\0\63"+
    "\0\u0b8e\0\u0bc1\0\u0bf4\0\63\0\u0c27\0\63\0\u0c5a\0\u0c8d"+
    "\0\u0cc0\0\u0cf3\0\u0d26\0\u0d59\0\u0d8c\0\u0dbf\0\u0df2\0\u0e25"+
    "\0\u0e58\0\u0e8b\0\u0ebe\0\u0ef1\0\u0f24\0\u0f57\0\u0f8a\0\u0fbd"+
    "\0\u0ff0\0\u1023\0\u1056\0\u1089\0\63\0\u10bc\0\u10ef\0\u1122"+
    "\0\u1155\0\u1188\0\u11bb\0\u11ee\0\u1221\0\u1254\0\u1287\0\u0bf4"+
    "\0\u12ba\0\u12ed\0\u1320\0\63\0\u1353\0\u1386\0\u13b9\0\u13ec"+
    "\0\u141f\0\u1452\0\u1485\0\u14b8\0\u14eb\0\u151e\0\u1551\0\u1584"+
    "\0\u15b7\0\u15ea\0\u161d\0\u1650\0\u1683\0\u16b6\0\u16e9\0\u171c"+
    "\0\63\0\63\0\u174f\0\u1782\0\u17b5\0\u17e8\0\u181b\0\63"+
    "\0\u184e\0\u1881\0\u18b4\0\u18e7\0\u191a\0\u194d\0\u1980\0\u19b3"+
    "\0\u19e6\0\u1a19\0\u1a4c\0\63\0\u1a7f\0\u1ab2\0\u1ae5\0\u1b18"+
    "\0\u1b4b\0\63\0\u1b7e\0\u1bb1\0\u1be4\0\u1c17\0\u1c4a\0\u1c7d"+
    "\0\u1cb0\0\u1ce3\0\u1d16\0\u1d49\0\u1d7c\0\u1daf\0\u1de2\0\63"+
    "\0\u1e15\0\u1e48\0\63\0\u1e7b\0\u1eae\0\u1ee1\0\u1f14\0\u1f47"+
    "\0\u1f7a\0\u1fad\0\u1fe0\0\u2013\0\u2046\0\u2079\0\u20ac\0\u20df"+
    "\0\u2112\0\63\0\u2145\0\u2178\0\u21ab\0\u21de\0\63\0\u2211"+
    "\0\u2244\0\63\0\u2277\0\63\0\63\0\u22aa\0\u22dd\0\u2310"+
    "\0\u2343\0\63\0\u2376\0\u23a9\0\u23dc\0\u240f\0\u2442\0\u2475"+
    "\0\u24a8\0\u24db\0\u250e\0\u2541\0\u2574\0\u25a7\0\u25da\0\u260d"+
    "\0\63\0\u2640\0\u2673\0\u26a6\0\u26d9\0\u270c\0\u273f\0\u2772"+
    "\0\63\0\u27a5\0\u27d8\0\63\0\u280b\0\u283e\0\u2871\0\u28a4"+
    "\0\63\0\63\0\u28d7\0\63\0\u290a\0\u293d\0\u2970\0\63"+
    "\0\u29a3\0\u29d6\0\u2a09\0\63\0\u2a3c\0\u2a6f\0\63\0\63"+
    "\0\63\0\u2aa2\0\u2ad5\0\u2b08\0\63\0\u2b3b\0\u2b6e\0\u2ba1"+
    "\0\u2bd4\0\u2c07\0\63\0\63\0\u2c3a\0\u2c6d\0\u2ca0\0\63"+
    "\0\u2cd3\0\u2d06\0\u2d39\0\u2d6c\0\u2d9f\0\63\0\u2dd2\0\u2e05"+
    "\0\u2e38\0\u2e6b\0\u2e9e\0\u2ed1\0\u2f04\0\u2f37\0\63\0\u2f6a"+
    "\0\63\0\u2f9d\0\u2fd0\0\u3003\0\u3036\0\u3069\0\63\0\63"+
    "\0\u309c\0\u30cf\0\u3102\0\u3135\0\u3168\0\63\0\63\0\u319b"+
    "\0\63\0\u31ce\0\63\0\63";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[308];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\0\1\3\1\5\1\2\1\6"+
    "\1\7\1\10\1\11\1\12\1\13\1\14\1\2\1\15"+
    "\1\16\1\17\1\2\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\1\2\1\27\1\30\3\2\1\31\1\32"+
    "\1\33\1\34\1\2\1\35\1\36\2\2\1\37\1\2"+
    "\1\40\2\2\1\41\1\30\1\36\1\5\1\2\64\0"+
    "\1\3\2\0\1\3\60\0\1\4\60\0\2\42\1\43"+
    "\2\42\1\43\1\42\1\0\52\42\1\43\6\0\1\44"+
    "\15\0\27\45\32\0\1\46\52\0\1\47\4\0\1\50"+
    "\61\0\1\51\1\0\1\16\103\0\1\52\63\0\1\53"+
    "\44\0\1\54\15\0\1\55\50\0\1\56\67\0\1\57"+
    "\3\0\1\60\4\0\1\61\11\0\1\61\26\0\1\62"+
    "\7\0\1\63\13\0\1\64\6\0\1\63\27\0\1\65"+
    "\76\0\1\66\1\67\45\0\1\70\3\0\1\71\3\0"+
    "\1\72\5\0\1\73\14\0\1\72\45\0\1\74\5\0"+
    "\1\75\57\0\1\76\5\0\1\77\33\0\1\100\15\0"+
    "\1\101\50\0\1\102\62\0\1\103\3\0\1\104\22\0"+
    "\1\104\33\0\1\105\52\0\1\106\3\0\27\106\64\0"+
    "\1\107\15\0\1\110\53\0\12\111\1\112\4\111\1\113"+
    "\43\111\2\50\1\114\1\0\1\115\56\50\20\0\1\51"+
    "\71\0\1\116\75\0\1\117\47\0\1\120\15\0\1\121"+
    "\55\0\1\122\1\123\74\0\1\124\37\0\1\125\101\0"+
    "\1\126\66\0\1\127\46\0\1\130\64\0\1\131\62\0"+
    "\1\132\47\0\1\133\17\0\1\134\11\0\1\134\45\0"+
    "\1\135\51\0\1\136\1\0\1\137\22\0\1\137\55\0"+
    "\1\140\51\0\1\141\5\0\1\142\43\0\1\143\61\0"+
    "\1\144\102\0\1\145\53\0\1\146\70\0\1\147\11\0"+
    "\1\147\47\0\1\150\64\0\1\151\56\0\1\152\3\0"+
    "\1\153\45\0\1\154\75\0\1\155\35\0\1\106\3\0"+
    "\27\106\1\156\27\0\1\157\42\0\12\111\1\112\62\111"+
    "\1\112\4\111\1\160\43\111\17\0\1\47\45\0\1\114"+
    "\117\0\1\161\53\0\1\162\56\0\1\163\100\0\1\164"+
    "\73\0\1\165\46\0\1\166\51\0\1\167\76\0\1\170"+
    "\47\0\1\171\4\0\1\172\22\0\1\172\51\0\1\173"+
    "\11\0\1\173\55\0\1\174\35\0\1\175\64\0\1\176"+
    "\101\0\1\177\60\0\1\200\62\0\1\201\43\0\1\202"+
    "\76\0\1\203\62\0\1\204\62\0\1\205\61\0\1\206"+
    "\71\0\1\207\42\0\1\210\66\0\1\211\22\0\1\211"+
    "\27\0\1\212\66\0\1\213\62\0\1\214\74\0\1\215"+
    "\70\0\1\216\41\0\1\217\53\0\1\106\3\0\27\106"+
    "\1\220\27\0\1\157\34\0\1\45\35\0\1\221\73\0"+
    "\1\222\47\0\1\223\70\0\1\224\6\0\1\225\5\0"+
    "\1\226\5\0\1\224\45\0\1\227\70\0\1\230\53\0"+
    "\1\231\55\0\1\232\22\0\1\232\35\0\1\233\72\0"+
    "\1\234\51\0\1\235\6\0\1\236\2\0\1\237\2\0"+
    "\1\240\11\0\1\240\36\0\1\241\22\0\1\241\50\0"+
    "\1\242\41\0\1\243\72\0\1\244\22\0\1\244\33\0"+
    "\1\245\66\0\1\246\22\0\1\246\50\0\1\247\62\0"+
    "\1\250\44\0\1\251\102\0\1\252\52\0\1\253\70\0"+
    "\1\254\56\0\1\255\70\0\1\256\60\0\1\257\56\0"+
    "\1\260\45\0\1\261\62\0\1\262\62\0\1\263\105\0"+
    "\1\264\54\0\1\265\45\0\1\266\62\0\1\267\111\0"+
    "\1\270\56\0\1\271\41\0\1\272\65\0\1\273\101\0"+
    "\1\274\62\0\1\275\42\0\1\276\3\0\1\277\22\0"+
    "\1\277\27\0\1\300\66\0\1\301\3\0\1\302\22\0"+
    "\1\302\45\0\1\303\73\0\1\304\47\0\1\305\70\0"+
    "\1\306\11\0\1\306\44\0\1\307\73\0\1\310\62\0"+
    "\1\311\51\0\1\312\65\0\1\313\57\0\1\314\62\0"+
    "\1\315\46\0\1\316\70\0\1\317\22\0\1\317\44\0"+
    "\1\320\50\0\1\321\74\0\1\322\51\0\1\323\60\0"+
    "\1\324\101\0\1\325\54\0\1\326\51\0\1\327\60\0"+
    "\1\330\66\0\1\331\62\0\1\332\56\0\1\333\77\0"+
    "\1\334\70\0\1\335\43\0\1\336\77\0\1\337\47\0"+
    "\1\340\71\0\1\341\65\0\1\342\52\0\1\343\22\0"+
    "\1\343\27\0\1\344\100\0\1\345\62\0\1\346\44\0"+
    "\1\347\72\0\1\350\22\0\1\350\50\0\1\351\41\0"+
    "\1\352\66\0\1\353\77\0\1\354\62\0\1\355\41\0"+
    "\1\356\100\0\1\357\62\0\1\360\65\0\1\361\63\0"+
    "\1\362\11\0\1\362\47\0\1\363\54\0\1\364\51\0"+
    "\1\365\76\0\1\366\61\0\1\367\45\0\1\370\106\0"+
    "\1\371\62\0\1\372\57\0\1\373\62\0\1\374\70\0"+
    "\1\375\62\0\1\376\55\0\1\377\11\0\1\377\26\0"+
    "\1\u0100\103\0\1\u0101\62\0\1\u0102\45\0\1\u0103\72\0"+
    "\1\u0104\67\0\1\u0105\56\0\1\u0106\51\0\1\u0107\66\0"+
    "\1\u0108\22\0\1\u0108\32\0\1\u0109\102\0\1\u010a\54\0"+
    "\1\u010b\51\0\1\u010c\105\0\1\u010d\43\0\1\u010e\22\0"+
    "\1\u010e\37\0\1\u010f\22\0\1\u010f\45\0\1\u0110\60\0"+
    "\1\u0111\52\0\1\u0112\101\0\1\u0113\54\0\1\u0114\63\0"+
    "\1\u0115\62\0\1\u0116\65\0\1\u0117\56\0\1\u0118\53\0"+
    "\1\u0119\62\0\1\u011a\60\0\1\u011b\101\0\1\u011c\60\0"+
    "\1\u011d\64\0\1\u011e\54\0\1\u011f\45\0\1\u0120\75\0"+
    "\1\u0121\73\0\1\u0122\62\0\1\u0123\53\0\1\u0124\66\0"+
    "\1\u0125\41\0\1\u0126\66\0\1\u0127\100\0\1\u0128\11\0"+
    "\1\u0128\26\0\1\u0129\62\0\1\u012a\105\0\1\u012b\55\0"+
    "\1\u012c\65\0\1\u012d\54\0\1\u012e\62\0\1\u012f\47\0"+
    "\1\u0130\104\0\1\u0131\11\0\1\u0131\36\0\1\u0132\22\0"+
    "\1\u0132\42\0\1\u0133\65\0\1\u0134\20\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[12801];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\4\1\5\11\3\1\2\11\20\1\1\11"+
    "\1\0\1\11\2\0\1\11\2\0\1\1\24\0\1\11"+
    "\5\0\1\11\3\0\1\11\3\0\1\11\1\1\1\11"+
    "\12\0\1\1\13\0\1\11\10\0\1\1\1\0\1\1"+
    "\3\0\1\11\24\0\2\11\5\0\1\11\13\0\1\11"+
    "\5\0\1\11\1\1\3\0\2\1\7\0\1\11\2\0"+
    "\1\11\16\0\1\11\4\0\1\11\2\0\1\11\1\0"+
    "\2\11\4\0\1\11\16\0\1\11\7\0\1\11\2\0"+
    "\1\11\4\0\2\11\1\0\1\11\3\0\1\11\2\0"+
    "\1\1\1\11\2\0\3\11\3\0\1\11\5\0\2\11"+
    "\3\0\1\11\5\0\1\11\10\0\1\11\1\0\1\11"+
    "\5\0\2\11\5\0\2\11\1\0\1\11\1\0\2\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[308];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  @SuppressWarnings("unused")
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lex(java.io.Reader in) {
      yyline = 1;
    yychar = 1;
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new java.lang.Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
  yyclose();    }
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  @Override  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          zzR = false;
          break;
        case '\r':
          yyline++;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
          }
          break;
        default:
          zzR = false;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { return new java_cup.runtime.Symbol(sym.EOF); }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { String msg = "Error Lexico \n"
        + "En la Linea: " + yyline + "\n"
        + " En La Columna: " + (int)yychar +"\n"
        + " Lexema: " + yytext() + "\n";

     ListError.getInstance().addError(new Error((int)yychar, yyline, yytext(), msg, "LEXICO"));
            }
            // fall through
          case 66: break;
          case 2:
            { 
            }
            // fall through
          case 67: break;
          case 3:
            { yychar = 1;
            }
            // fall through
          case 68: break;
          case 4:
            { return new Symbol(sym.PAbre, (int)yychar, yyline, yytext());
            }
            // fall through
          case 69: break;
          case 5:
            { return new Symbol(sym.PCierra, (int)yychar, yyline, yytext());
            }
            // fall through
          case 70: break;
          case 6:
            { return new Symbol(sym.Producto, (int)yychar, yyline, yytext());
            }
            // fall through
          case 71: break;
          case 7:
            { return new Symbol(sym.Suma, (int)yychar, yyline, yytext());
            }
            // fall through
          case 72: break;
          case 8:
            { return new Symbol(sym.Coma, (int)yychar, yyline, yytext());
            }
            // fall through
          case 73: break;
          case 9:
            { return new Symbol(sym.Resta, (int)yychar, yyline, yytext());
            }
            // fall through
          case 74: break;
          case 10:
            { return new Symbol(sym.Division, (int)yychar, yyline, yytext());
            }
            // fall through
          case 75: break;
          case 11:
            { return new Symbol(sym.Numero, (int)yychar, yyline, yytext());
            }
            // fall through
          case 76: break;
          case 12:
            { return new Symbol(sym.PComa, (int)yychar, yyline, yytext());
            }
            // fall through
          case 77: break;
          case 13:
            { return new Symbol(sym.IntCierra, (int)yychar, yyline, yytext());
            }
            // fall through
          case 78: break;
          case 14:
            { return new Symbol(sym.IntAbre, (int)yychar, yyline, yytext());
            }
            // fall through
          case 79: break;
          case 15:
            { return new Symbol(sym.Cadena, (int)yychar, yyline, yytext());
            }
            // fall through
          case 80: break;
          case 16:
            { return new Symbol(sym.Flecha, (int)yychar, yyline, yytext());
            }
            // fall through
          case 81: break;
          case 17:
            { return new Symbol(sym.Or, (int)yychar, yyline, yytext());
            }
            // fall through
          case 82: break;
          case 18:
            { return new Symbol(sym.Si, (int)yychar, yyline, yytext());
            }
            // fall through
          case 83: break;
          case 19:
            { return new Symbol(sym.Caracter, (int)yychar, yyline, yytext());
            }
            // fall through
          case 84: break;
          case 20:
            { return new Symbol(sym.And, (int)yychar, yyline, yytext());
            }
            // fall through
          case 85: break;
          case 21:
            { return new Symbol(sym.Fin, (int)yychar, yyline, yytext());
            }
            // fall through
          case 86: break;
          case 22:
            { return new Symbol(sym.Not, (int)yychar, yyline, yytext());
            }
            // fall through
          case 87: break;
          case 23:
            { return new Symbol(sym.Identificador, (int)yychar, yyline, yytext());
            }
            // fall through
          case 88: break;
          case 24:
            { return new Symbol(sym.Como, (int)yychar, yyline, yytext());
            }
            // fall through
          case 89: break;
          case 25:
            { return new Symbol(sym.OSi, (int)yychar, yyline, yytext());
            }
            // fall through
          case 90: break;
          case 26:
            { return new Symbol(sym.Para, (int)yychar, yyline, yytext());
            }
            // fall through
          case 91: break;
          case 27:
            { return new Symbol(sym.False, (int)yychar, yyline, yytext());
            }
            // fall through
          case 92: break;
          case 28:
            { return new Symbol(sym.Hacer, (int)yychar, yyline, yytext());
            }
            // fall through
          case 93: break;
          case 29:
            { return new Symbol(sym.Hasta, (int)yychar, yyline, yytext());
            }
            // fall through
          case 94: break;
          case 30:
            { return new Symbol(sym.Mayor, (int)yychar, yyline, yytext());
            }
            // fall through
          case 95: break;
          case 31:
            { return new Symbol(sym.Menor, (int)yychar, yyline, yytext());
            }
            // fall through
          case 96: break;
          case 32:
            { return new Symbol(sym.Segun, (int)yychar, yyline, yytext());
            }
            // fall through
          case 97: break;
          case 33:
            { return new Symbol(sym.TCadena, (int)yychar, yyline, yytext());
            }
            // fall through
          case 98: break;
          case 34:
            { return new Symbol(sym.FinSi, (int)yychar, yyline, yytext());
            }
            // fall through
          case 99: break;
          case 35:
            { return new Symbol(sym.Inicio, (int)yychar, yyline, yytext());
            }
            // fall through
          case 100: break;
          case 36:
            { return new Symbol(sym.Metodo, (int)yychar, yyline, yytext());
            }
            // fall through
          case 101: break;
          case 37:
            { return new Symbol(sym.Modulo, (int)yychar, yyline, yytext());
            }
            // fall through
          case 102: break;
          case 38:
            { return new Symbol(sym.TNumero, (int)yychar, yyline, yytext());
            }
            // fall through
          case 103: break;
          case 39:
            { return new Symbol(sym.TBoolean, (int)yychar, yyline, yytext());
            }
            // fall through
          case 104: break;
          case 40:
            { return new Symbol(sym.Funcion, (int)yychar, yyline, yytext());
            }
            // fall through
          case 105: break;
          case 41:
            { return new Symbol(sym.Repetir, (int)yychar, yyline, yytext());
            }
            // fall through
          case 106: break;
          case 42:
            { return new Symbol(sym.TChar, (int)yychar, yyline, yytext());
            }
            // fall through
          case 107: break;
          case 43:
            { return new Symbol(sym.Ejecutar, (int)yychar, yyline, yytext());
            }
            // fall through
          case 108: break;
          case 44:
            { return new Symbol(sym.Entonces, (int)yychar, yyline, yytext());
            }
            // fall through
          case 109: break;
          case 45:
            { return new Symbol(sym.Igual, (int)yychar, yyline, yytext());
            }
            // fall through
          case 110: break;
          case 46:
            { return new Symbol(sym.FinPara, (int)yychar, yyline, yytext());
            }
            // fall through
          case 111: break;
          case 47:
            { return new Symbol(sym.Imprimir, (int)yychar, yyline, yytext());
            }
            // fall through
          case 112: break;
          case 48:
            { return new Symbol(sym.Ingresar, (int)yychar, yyline, yytext());
            }
            // fall through
          case 113: break;
          case 49:
            { return new Symbol(sym.Mientras, (int)yychar, yyline, yytext());
            }
            // fall through
          case 114: break;
          case 50:
            { return new Symbol(sym.Potencia, (int)yychar, yyline, yytext());
            }
            // fall through
          case 115: break;
          case 51:
            { return new Symbol(sym.Retornar, (int)yychar, yyline, yytext());
            }
            // fall through
          case 116: break;
          case 52:
            { return new Symbol(sym.ConValor, (int)yychar, yyline, yytext());
            }
            // fall through
          case 117: break;
          case 53:
            { return new Symbol(sym.FinSegun, (int)yychar, yyline, yytext());
            }
            // fall through
          case 118: break;
          case 54:
            { return new Symbol(sym.HastaQue, (int)yychar, yyline, yytext());
            }
            // fall through
          case 119: break;
          case 55:
            { return new Symbol(sym.True, (int)yychar, yyline, yytext());
            }
            // fall through
          case 120: break;
          case 56:
            { return new Symbol(sym.FinMetodo, (int)yychar, yyline, yytext());
            }
            // fall through
          case 121: break;
          case 57:
            { return new Symbol(sym.FinFuncion, (int)yychar, yyline, yytext());
            }
            // fall through
          case 122: break;
          case 58:
            { return new Symbol(sym.ImprimirNl, (int)yychar, yyline, yytext());
            }
            // fall through
          case 123: break;
          case 59:
            { return new Symbol(sym.Diferente, (int)yychar, yyline, yytext());
            }
            // fall through
          case 124: break;
          case 60:
            { return new Symbol(sym.FinMientras, (int)yychar, yyline, yytext());
            }
            // fall through
          case 125: break;
          case 61:
            { return new Symbol(sym.MayorIg, (int)yychar, yyline, yytext());
            }
            // fall through
          case 126: break;
          case 62:
            { return new Symbol(sym.MenorIg, (int)yychar, yyline, yytext());
            }
            // fall through
          case 127: break;
          case 63:
            { return new Symbol(sym.ConParametros, (int)yychar, yyline, yytext());
            }
            // fall through
          case 128: break;
          case 64:
            { return new Symbol(sym.Incremental, (int)yychar, yyline, yytext());
            }
            // fall through
          case 129: break;
          case 65:
            { return new Symbol(sym.DeLoContrario, (int)yychar, yyline, yytext());
            }
            // fall through
          case 130: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
