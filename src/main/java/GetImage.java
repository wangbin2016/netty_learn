
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class GetImage {
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		getHtml();
	}
	
	public static void getHtml(){
		//System.out.println(getUrlByChrome("http://m.nanrenvip.net/tianhaiyi/2017/RBD-821.html","utf-8"));
		
		getImage();
	}
	
	public static void getImage(){
		       //http://img3.nanrenvip.net/uploads/2017/04/rbd00821pl.jpg
		String url = "http://img3.nanrenvip.net/uploads/2017/04/rbd00821pl.jpg";
		byte[] btImg = getImageFromNetByUrl(url);
		if(null != btImg && btImg.length > 0){
			System.out.println("读取到：" + btImg.length + " 字节");
			String fileName = "rbd00821pl.jpg";
			writeImageToDisk(btImg, fileName);
		}else{
			System.out.println("没有从该连接获得内容");
		}
	}
	
	/**
	 * 将图片写入到磁盘
	 * @param img 图片数据流
	 * @param fileName 文件保存时的名称
	 */
	public static void writeImageToDisk(byte[] img, String fileName){
		try {
			File file = new File("e:\\" + fileName);
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
			System.out.println("图片已经写入到C盘");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据地址获得数据的字节流
	 * @param strUrl 网络连接地址
	 * @return
	 */
	public static byte[] getImageFromNetByUrl(String strUrl){
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			conn.setRequestProperty("Cache-Control:", "max-age=0");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
			conn.setRequestProperty("Referer", strUrl);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从输入流中获取数据
	 * @param inStream 输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=inStream.read(buffer)) != -1 ){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
	
	/**
	 * 模拟chrome发送请求
	 */
	@SuppressWarnings("unused")
	static public String getUrlByChrome(String urlString,String charsetCode) {

		URL url = null;
		URLConnection connection = null;
		InputStream in = null;

		if (urlString != null && !urlString.trim().startsWith("http:")) {
			return "";
		}

		try {
			url = new URL(urlString);
			//匹配url为500的 就用代理
			Pattern pattern500 = Pattern.compile("\\.(500|500wan)\\.");
			Matcher matcher500 = pattern500.matcher(urlString);
			
			//匹配url为ydniu的 就用代理
			Pattern patternYdniu = Pattern.compile("\\.(ydniu)\\.");
			Matcher matcherYdniu = patternYdniu.matcher(urlString);
			
			if(false && matcher500.find()){
				SocketAddress addr = new InetSocketAddress("117.177.250.152",80);//代理地址
				Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);
				connection = url.openConnection(typeProxy);
			} else if(false && matcherYdniu.find()){
				SocketAddress addr = new InetSocketAddress("223.67.136.218",8000);//代理地址
				Proxy typeProxy = new Proxy(Proxy.Type.HTTP, addr);
				connection = url.openConnection(typeProxy);
			}else{
				connection=url.openConnection();
			}
			
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);
			connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			connection.setRequestProperty("Cache-Control:", "max-age=0");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
			connection.setRequestProperty("Referer", urlString);

			String charset = charsetCode; // default IE charset
			String encoding = "";
			if (connection instanceof HttpURLConnection) {

				HttpURLConnection http = (HttpURLConnection) connection;
				encoding = http.getContentEncoding();

			}
			// com.cailele.lottery.tools.LogUtil.out(connection.getContentEncoding());
			@SuppressWarnings("rawtypes")
			Map headers = connection.getHeaderFields();
			if (headers.size() > 0) {
				String response = headers.get(null).toString();
				if (response.indexOf("200 OK") < 0) {
					throw new Exception("读取地址:" + url + " 错误:" + response);
				}
				/*
				 * com.cailele.lottery.tools.LogUtil.out(headers.keySet().toArray().length);
				 * for(Object o:headers.keySet().toArray()) {
				 * com.cailele.lottery.tools.LogUtil.out(o==null?"":o.toString()
				 * +"="+headers.get(o)); } //
				 */
				// *
				try {
					String contentType = headers.get("Content-Type").toString().replaceAll("\\[|\\]|\\\"", "");
					String ct[] = contentType.split(";");
					if (ct.length > 1) {
						String[] cs = ct[1].split("=");
						if (cs.length > 1) {
							charset = cs[1];
						}
					}
				} catch (Exception e) {
				}
				// */
			}

			if (("gzip").equals(encoding)) {
				in = new GZIPInputStream(connection.getInputStream());
			} else {
				in = connection.getInputStream();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
			StringBuffer sb = new StringBuffer();
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				sb.append(temp + "\r\n");
			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {

			}
		}
		return null;

	}
}