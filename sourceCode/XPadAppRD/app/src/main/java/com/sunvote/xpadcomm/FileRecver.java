package com.sunvote.xpadcomm;

import android.os.Environment;
import android.util.Log;

import com.sunvote.util.LogUtil;
import com.sunvote.xpadapp.utils.FileUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FileRecver {
	private String TAG = "FileRecver";
	private String ip;
	private int port;
	private Socket socket = null;
	DataOutputStream out = null;
	DataInputStream getMessageStream = null;
	private String filePath = Environment.getExternalStorageDirectory().getPath() + "/sunvote";
	private String fileName;
	private String keypadID;
	private FileReciverInterface listener;

	public interface FileReciverInterface {
		void onConnectServerError();

		void onDownloadDataError();

		void onDownload(long percent);

		void onDownloadSuccess();

	}

	private static FileRecver instance;

	public static FileRecver getInstance(FileReciverInterface recvInterface, String ip, int port) {
		if(instance == null){
			instance = new FileRecver(recvInterface,ip,port);
		}
		instance.listener = recvInterface;
		instance.ip = ip;
		instance.port = port;
		return instance;
	}

	public FileRecver(FileReciverInterface recvInterface, String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.listener = recvInterface;
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
			if (file.exists()) {
				Log.d(TAG, "create dir ok!");
			}
		}
	}

	public void tryGetMeetingFiles(int keyId) {
		keypadID = String.valueOf(keyId);
		if (createConnection()) {
			sendMessage(keyId + "\n");
			lastReceveLength = 0;
			passedlen = 0 ;
			CheckReceveThread checkReceveThread = new CheckReceveThread();
			checkReceveThread.start();

			getMeetingFiles();

		}
	}

	public long lastReceveLength;
	private int compareCount;
	private class  CheckReceveThread  extends  Thread{
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
					if(lastReceveLength != passedlen ){
						lastReceveLength = passedlen;
						//sendMessage(keypadID + ":recv"+lastReceveLength+"\n");
						LogUtil.d(TAG,"CheckReceveThread:check ok!");
						compareCount = 0;
					}else{
						compareCount ++;
						if(compareCount == 60){
							compareCount = 0;
							LogUtil.d(TAG,"CheckReceveThread:60s no response ,fail!");
							shutDownConnection();
							Thread.sleep(1000);
							listener.onDownloadDataError();
							break;
						}
					}



					if (passedlen > 0 && passedlen == fileLen) {
						LogUtil.d(TAG,"CheckReceveThread:success!");
						break;
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	};

	public boolean createConnection() {
		for(int i=0;i<3	;i++) {
			try {
				socket = new Socket(ip, port);
				return true;
			} catch (Exception e) {
				listener.onConnectServerError();
				e.printStackTrace();
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		return false;
	}


	public void sendMessage(String sendMessage) {
		try {
			out = new DataOutputStream(socket.getOutputStream());

			byte[] midbytes = sendMessage.getBytes("UTF8");
			out.write(midbytes);
			// out.writeUTF(sendMessage);
			out.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public long passedlen = 0;
	public long fileLen = 0;

	public void getMeetingFiles() {
		DataInputStream inputStream = null;
		inputStream = getDataInStream();
		try {
			String savePath = null;
			int bufferSize = 512000;// 8192
			byte[] buf = new byte[bufferSize];


			long tmpTime = System.currentTimeMillis();
			String strln = null;
			while (strln == null) {
				strln = inputStream.readLine();
				if (strln != null) {
					Log.d(TAG, strln);
					String[] ary = strln.split(",");
					if (ary.length == 2) {
						fileLen = Long.parseLong(ary[0]);
						fileName = ary[1];
						savePath = filePath + "/" + fileName;
						break;
					}
				}
				if (System.currentTimeMillis() - tmpTime > 20 * 1000) {
					Log.e(TAG, "get file name and fileLen time out!");
					break;
				}
				// strln = inputStream.readLine();
			}
			if (savePath == null) {
				Log.e(TAG, "savePath==null");
				listener.onConnectServerError();
				return;
			}
			File saveFile = new File(savePath);
			if (saveFile.exists()) {
				saveFile.delete();
			}
			// savePath += inputStream.readUTF();
			// log.d("AndroidClient","@@@savePath"+savePath);
			DataOutputStream fileOut = new DataOutputStream(
					new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(savePath))));
			// len = inputStream.readLong();
			// Log.d(TAG,"文件的长度为:"+len);
			Log.d(TAG, "start recv");
			while (true) {
				int read = 0;
				if (inputStream != null) {
					LogUtil.d(TAG, "inputStream.read");
					read = inputStream.read(buf);
					LogUtil.d(TAG, "inputStream.read len:"+ read);
				}
				passedlen += read;
				if (read == -1) {
					break;
				}
				long per = passedlen * 100 / fileLen;
				Log.d(TAG, "file recv" + per + "%/n");
				LogUtil.d(TAG, "file recv" + per + "%/n");
				if (per < 0) {
					Log.d(TAG, "error");
				}
				listener.onDownload(per);

				if(read>0) {
					fileOut.write(buf, 0, read);
				}
				if (passedlen == fileLen) {
					break;
				}
			}
			// Log.d("AndroidClient", "@@@文件接收完成" + savePath);
			Log.d(TAG, "recv file 100%");
			fileOut.close();
			String outDirName;
			try {

				if (fileName.endsWith(".zip")) {
					outDirName = fileName.substring(0, fileName.length() - 4);
					String uzipDirPath = filePath + "/" + outDirName;
					File unzipDir = new File(uzipDirPath);
					if (unzipDir.exists()) {
						try {
							LogUtil.d(TAG, "clear dir");
							FileUtil.deleteFileInDir(unzipDir);
						}catch (Exception e){
							e.printStackTrace();
						}

					}else{
						boolean ret=false;
						try {
							ret = unzipDir.mkdir();
						} catch (Exception e){
							e.printStackTrace();
						}
						if(ret) {
							LogUtil.d(TAG, "createFile Ok, unzip!");
						}else{
							LogUtil.d(TAG,"createFile fail !"+ uzipDirPath);
						}
					}

					Log.d(TAG, "unzip");
					AntZipUtil.unZip(filePath + "/" + fileName, uzipDirPath);
					Log.d(TAG, "unzip ok");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			sendMessage(keypadID + ":success\n");
			Log.d(TAG, "send success");
			int answerTimes = 0;
			while (answerTimes < 30) {
				Log.d(TAG, "send success");
				sendMessage(keypadID + ":success\n");
				strln = inputStream.readLine();
				if (strln != null) {
					Log.d(TAG, "=======recv:" + strln);
					if (strln.equals("success")) {
						Log.d(TAG, "recv server seccuss");
						break;
					}
				}
				answerTimes++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (answerTimes == 30) {
				Log.d(TAG, "wait answer timeout");
			}
			shutDownConnection();// 关掉socket连接

			listener.onDownloadSuccess();

			// UnZip.unzipFile(filePath+"/"+fileName , filePath+"/metting/");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DataInputStream getDataInStream() {
		try {
			getMessageStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			// return getMessageStream;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (getMessageStream != null) {
				try {
					getMessageStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return getMessageStream;
	}

	public void shutDownConnection() {
		try {
			if (out != null) {
				out.close();
			}
			if (getMessageStream != null) {
				getMessageStream.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
