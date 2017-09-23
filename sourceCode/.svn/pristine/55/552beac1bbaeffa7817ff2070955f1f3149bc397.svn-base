package com.sunvote.util;

import android.annotation.SuppressLint;

/**
 * 车主APP<br>
 * 作者： 孙向锦<br>
 * 时间： 2016/10/10<br>
 * 公司：长沙硕铠电子科技有限公司<br>
 * Email：sunxiangjin@soarsky-e.com<br>
 * 公司网址：http://www.soarsky-e.com<br>
 * 公司地址（Add）  ：湖南省长沙市岳麓区麓谷信息港A座8楼<br>
 * 版本：1.0.0.0<br>
 * 邮编：410000<br>
 * 程序功能：<br>
 * 动画控制<br>
 */
@SuppressLint("UseValueOf")
public class ByteUtil {
	private ByteUtil() {
        System.out.println("ByteUtil Constructor");
    }

	public static String bytesToString(byte[] b) {
		StringBuffer result = new StringBuffer("");
		int length = b.length;

		for (int i = 0; i < length; i++) {
			char ch = (char)(b[i] & 0xff);
			if (ch == 0) {
				break;
			}

			result.append(ch);
		}

		return result.toString();
	}


	public static String bytesToString(byte[] b,int start,int end) {
		StringBuffer result = new StringBuffer("");

		for (int i = start; i < end; i++) {
			char ch = (char)(b[i] & 0xff);
			if (ch == 0) {
				break;
			}

			result.append(ch);
		}

		return result.toString();
	}


	/**
	 * 数组转int
	 * @param b
	 * @return
     */
	public static int byteArrayToInt(byte[] b) {
		int length=b.length;
		int value= 0;
		for (int i = 0; i < length; i++) {
			int shift= (length - 1 - i) * 8;
			value +=(b[i] & 0x000000FF) << shift;//往高位游
		}
		return value;
	}




	public static int HexToInt(String strHex){
		int nResult = 0;
		if ( !IsHex(strHex) )
			return nResult;
		String str = strHex.toUpperCase();
		if ( str.length() > 2 ){
			if ( str.charAt(0) == '0' && str.charAt(1) == 'X' ){
				str = str.substring(2);
			}
		}
		int nLen = str.length();
		for ( int i=0; i<nLen; ++i ){
			char ch = str.charAt(nLen-i-1);
			try {
				nResult += (GetHex(ch)*GetPower(16, i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nResult;
	}



	//计算16进制对应的数值
	public static int GetHex(char ch) throws Exception{
		if ( ch>='0' && ch<='9' )
			return (int)(ch-'0');
		if ( ch>='a' && ch<='f' )
			return (int)(ch-'a'+10);
		if ( ch>='A' && ch<='F' )
			return (int)(ch-'A'+10);
		throw new Exception("error param");
	}

	//计算幂
	public static int GetPower(int nValue, int nCount) throws Exception{
		if ( nCount <0 )
			throw new Exception("nCount can't small than 1!");
		if ( nCount == 0 )
			return 1;
		int nSum = 1;
		for ( int i=0; i<nCount; ++i ){
			nSum = nSum*nValue;
		}
		return nSum;
	}
	//判断是否是16进制数
	public static boolean IsHex(String strHex){
		int i = 0;
		if ( strHex.length() > 2 ){
			if ( strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x') ){
				i = 2;
			}
		}
		for ( ; i<strHex.length(); ++i ){
			char ch = strHex.charAt(i);
			if ( (ch>='0' && ch<='9') || (ch>='A' && ch<='F') || (ch>='a' && ch<='f') )
				continue;
			return false;
		}
		return true;
	}


	public static String bytearrayToHexString(byte[] b, int leng) {
		StringBuffer strbuf = new StringBuffer();

		for (int i = 0; i < leng; i++) {
			strbuf.append("0123456789ABCDEF".charAt(((byte) ((b[i] & 0xf0) >> 4))));
			strbuf.append("0123456789ABCDEF".charAt((byte) (b[i] & 0x0f)));
			strbuf.append(" ");
		}
		return strbuf.toString();
	}

	public static byte[] stringToBytes(String s) {
		return s.getBytes();
	}
	
	public static void ShortToBytes(byte[] b, short x, int offset) {
		//byte[] b = new byte[2];
		
		if (b.length-offset >= 2) {
			b[offset + 1] = (byte) (x >> 8);
			b[offset + 0] = (byte) (x >> 0);
		}
		
		//return b;
	}

	public static short BytesToShort(byte[] b, int offset) {
		short x = 0;
		if (b.length-offset >= 2) {
			x = (short) (((b[offset + 1] << 8) | b[offset + 0] & 0xff));
		}
		
		return x;
	}
	
	//字节转为字符�?
	public static String byteToHexString(byte b) {

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("0123456789ABCDEF".charAt((int) ((b >> 4) & 0x0F)));
		sbBuffer.append("0123456789ABCDEF".charAt((int) (b & 0x0F)));
		return sbBuffer.toString();
	}

	public static void IntToBytes(byte[] b, int x, int offset) {
		//byte[] b = new byte[4];
		
		if (b.length-offset >= 4) {
			b[offset + 3] = (byte) (x >> 24);
			b[offset + 2] = (byte) (x >> 16);
			b[offset + 1] = (byte) (x >> 8);
			b[offset + 0] = (byte) (x >> 0);
		}
		
		//return b;
	}

	public static int BytesToInt(byte[] b, int offset) {
		int x = 0;
		if (b.length-offset >= 4) {
			x = (int) ((((b[offset + 3] & 0xff) << 24)
					| ((b[offset + 2] & 0xff) << 16)
					| ((b[offset + 1] & 0xff) << 8) | ((b[offset + 0] & 0xff) << 0)));
		}
		
		return x;
	}

	public static void LongToBytes(byte[] b, long x, int offset) {
		//byte[] b = new byte[8];
		
		if (b.length-offset >= 8) {
			b[offset + 7] = (byte) (x >> 56);
			b[offset + 6] = (byte) (x >> 48);
			b[offset + 5] = (byte) (x >> 40);
			b[offset + 4] = (byte) (x >> 32);
			b[offset + 3] = (byte) (x >> 24);
			b[offset + 2] = (byte) (x >> 16);
			b[offset + 1] = (byte) (x >> 8);
			b[offset + 0] = (byte) (x >> 0);
		}
		
		//return b;
	}

	public static long BytesToLong(byte[] b, int offset) {
		long x = 0;
		if (b.length-offset >= 8) {
			x = ((((long) b[offset + 7] & 0xff) << 56)
					| (((long) b[offset + 6] & 0xff) << 48)
					| (((long) b[offset + 5] & 0xff) << 40)
					| (((long) b[offset + 4] & 0xff) << 32)
					| (((long) b[offset + 3] & 0xff) << 24)
					| (((long) b[offset + 2] & 0xff) << 16)
					| (((long) b[offset + 1] & 0xff) << 8) | (((long) b[offset + 0] & 0xff) << 0));
		}
		
		return x;
	}

	public static void CharToBytes(byte[] b, char ch, int offset) {
		// byte[] b = new byte[2];
		
		if (b.length-offset >= 2) {
			int temp = (int) ch;
			for (int i = 0; i < 2; i ++ ) {
				b[offset + i] = new Integer(temp & 0xff).byteValue(); // �����λ���������λ
				temp = temp >> 8; // ������8λ
			}
		}
		
		//return b;
	}

	public static char BytesToChar(byte[] b, int offset) {
		int s = 0;
		
		if (b.length-offset >= 2) {
			if (b[offset + 1] > 0)
				s += b[offset + 1];
			else
				s += 256 + b[offset + 0];
			s *= 256;
			if (b[offset + 0] > 0)
				s += b[offset + 1];
			else
				s += 256 + b[offset + 0];
		}
		
		char ch = (char) s;
		return ch;
	}

	public static void FloatToBytes(byte[] b, float x, int offset) {
		//byte[] b = new byte[4];
		
		if (b.length-offset >= 4) {
			int l = Float.floatToIntBits(x);
			for (int i = 0; i < 4; i++) {
				b[offset + i] = new Integer(l).byteValue();
				l = l >> 8;
			}
		}
		
		//return b;
	}

	public static float BytesToFloat(byte[] b, int offset) {
		int l = 0;
		
		if (b.length-offset >= 4) {
			l = b[offset + 0];
			l &= 0xff;
			l |= ((long) b[offset + 1] << 8);
			l &= 0xffff;
			l |= ((long) b[offset + 2] << 16);
			l &= 0xffffff;
			l |= ((long) b[offset + 3] << 24);
		}
		
		return Float.intBitsToFloat(l);
	}

	public static void DoubleToBytes(byte[] b, double x, int offset) {
		//byte[] b = new byte[8];
		
		if (b.length-offset >= 8) {
			long l = Double.doubleToLongBits(x);
			for (int i = 0; i < 4; i++) {
				b[offset + i] = new Long(l).byteValue();
				l = l >> 8;
			}
		}
		
		//return b;
	}

	public static double BytesToDouble(byte[] b, int offset) {
		long l = 0;
		
		if (b.length-offset >= 8) {
			l = b[0];
			l &= 0xff;
			l |= ((long) b[1] << 8);
			l &= 0xffff;
			l |= ((long) b[2] << 16);
			l &= 0xffffff;
			l |= ((long) b[3] << 24);
			l &= 0xffffffffl;
			l |= ((long) b[4] << 32);
			l &= 0xffffffffffl;
			l |= ((long) b[5] << 40);
			l &= 0xffffffffffffl;
			l |= ((long) b[6] << 48);
			l &= 0xffffffffffffffl;
			l |= ((long) b[7] << 56);
		}
		
		return Double.longBitsToDouble(l);
	}
	
	/**
	  * ��shortתΪ���ֽ���ǰ�����ֽ��ں�
	  */
	public static short toLH(short n) {
	  byte[] b = new byte[2];
	  b[0] = (byte) (n & 0xff);
	  b[1] = (byte) (n >> 8 & 0xff);
	  
	  short ret = BytesToShort(b, 0);
	  return ret;
	}
	
	/** 
	  * ��shortתΪ���ֽ���ǰ�����ֽ��ں�
	  */
	public static short toHL(short n) {
	  byte[] b = new byte[2];
	  b[1] = (byte) (n & 0xff);
	  b[0] = (byte) (n >> 8 & 0xff);
	  
	  short ret = BytesToShort(b, 0);
	  return ret;
	}
	
	/**
	  * ��intתΪ���ֽ���ǰ�����ֽ��ں�
	  */
	public static int toLH(int n) {
	  byte[] b = new byte[4];
	  b[0] = (byte) (n & 0xff);
	  b[1] = (byte) (n >> 8 & 0xff);
	  b[2] = (byte) (n >> 16 & 0xff);
	  b[3] = (byte) (n >> 24 & 0xff);
	  
	  int ret = BytesToInt(b, 0);
	  return ret;
	}
	  
	/**
	  * ��intתΪ���ֽ���ǰ�����ֽ��ں�
	  */
	public static int toHL(int n) {
	  byte[] b = new byte[4];
	  b[3] = (byte) (n & 0xff);
	  b[2] = (byte) (n >> 8 & 0xff);
	  b[1] = (byte) (n >> 16 & 0xff);
	  b[0] = (byte) (n >> 24 & 0xff);
	  
	  int ret = BytesToInt(b, 0);
	  return ret;
	}
	
	/**
	  * ��longתΪ���ֽ���ǰ�����ֽ��ں�
	  */
	public static long toLH(long n) {
	  byte[] b = new byte[8];
	  b[0] = (byte) (n & 0xff);
	  b[1] = (byte) (n >> 8 & 0xff);
	  b[2] = (byte) (n >> 16 & 0xff);
	  b[3] = (byte) (n >> 24 & 0xff);
	  b[4] = (byte) (n >> 32 & 0xff);
	  b[5] = (byte) (n >> 40 & 0xff);
	  b[6] = (byte) (n >> 48 & 0xff);
	  b[7] = (byte) (n >> 56 & 0xff);
	  
	  long ret = BytesToLong(b, 0);
	  return ret;
	}
	  
	/**
	  * ��longתΪ���ֽ���ǰ�����ֽ��ں�
	  */
	public static long toHL(long n) {
	  byte[] b = new byte[8];
	  b[7] = (byte) (n & 0xff);
	  b[6] = (byte) (n >> 8 & 0xff);
	  b[5] = (byte) (n >> 16 & 0xff);
	  b[4] = (byte) (n >> 24 & 0xff);
	  b[3] = (byte) (n >> 32 & 0xff);
	  b[2] = (byte) (n >> 40 & 0xff);
	  b[1] = (byte) (n >> 48 & 0xff);
	  b[0] = (byte) (n >> 56 & 0xff);
	  
	  long ret = BytesToLong(b, 0);
	  return ret;
	}
	
	//	private void OutputParamMain() {
	//	byte[] param = new byte[100];
	//	OutputParamByte(param);
	//	StringUtil.printBytes(param);
	//	short len = ByteUtil.decodeOutputBytes(param);
	//	StringUtil.printBytes(param, len);
	//}
	//
	//private void OutputParamByte(byte[] byParam) {
	//	byte[] by = {(byte)0x30,(byte)0x31,(byte)0x32,(byte)0x33,(byte)0x34,(byte)0x35,(byte)0x36,(byte)0x37,(byte)0x38,(byte)0x39};
	//	
	//	if (byParam.length >= by.length) {
	//		System.arraycopy(by, 0, byParam, 0, by.length);
	//		ByteUtil.encodeOutputBytes(byParam, (short)by.length);
	//	}
	//}
	/**
	  * ��byte[]���������б���(��ʵ�����ݳ��ȴ洢���ֽ�����ǰ2���ֽ�)
	  */
	public static void encodeOutputBytes(byte[] b, short sLen) {
		if (b.length >= sLen+2) {
			System.arraycopy(b, 0, b, 2, sLen);
			byte[] byShort = new byte[2];
			ShortToBytes(byShort, sLen, 0);
			System.arraycopy(byShort, 0, b, 0, byShort.length);
		}
	}
	
	/**
	  * ��byte[]���������з�����(��ʵ�����ݳ����Է���ֵ��ʽ����)
	  */
	public static short decodeOutputBytes(byte[] b) {
		byte[] byShort = new byte[2];
		System.arraycopy(b, 0, byShort, 0, byShort.length);
		short sLen = BytesToShort(byShort, 0);
		
		System.arraycopy(b, 2, b, 0, sLen);
		
		return sLen;
	}

	public static int byte1ToInt(byte b){
		int ret = 0 ;
		ret +=(b & 0x000000FF) ;
		return ret ;
	}

	/**
	 * 将short转成byte[2]
	 * @param a
	 * @return
	 */
	public static byte[] short2Byte(short a){
		byte[] b = new byte[2];

		b[0] = (byte) (a >> 8);
		b[1] = (byte) (a);

		return b;
	}
}