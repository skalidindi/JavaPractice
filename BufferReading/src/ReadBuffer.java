import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadBuffer {
	public static void main(String[] args) {
		// Old I/O way
		BufferedReader br = null;
        String sCurrentLine = null;
        try
        {
            br = new BufferedReader(new FileReader("data.csv"));
            while ((sCurrentLine = br.readLine()) != null)
            {
                System.out.println(sCurrentLine);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (br != null)
                br.close();
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        System.out.println("---------------------------------------------------------");
        // Read small file with buffer set as file size
        try
        {
            RandomAccessFile aFile = new RandomAccessFile("data.csv","r");
            FileChannel inChannel = aFile.getChannel();
            long fileSize = inChannel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            //buffer.rewind();
            buffer.flip();
            for (int i = 0; i < fileSize; i++)
            {
                System.out.print((char) buffer.get());
            }
            System.out.println("");
            inChannel.close();
            aFile.close();
        }
        catch (IOException exc)
        {
            System.out.println(exc);
            System.exit(1);
        }
        System.out.println("---------------------------------------------------------");
        // Read large file in chunks with fixed size buffer
		try {
			RandomAccessFile aFile = new RandomAccessFile("data.csv", "r");
	        FileChannel inChannel = aFile.getChannel();
	        ByteBuffer buffer = ByteBuffer.allocate(1024);
	        while(inChannel.read(buffer) > 0)
	        {
	            buffer.flip();
	            for (int i = 0; i < buffer.limit(); i++)
	            {
	                System.out.print((char) buffer.get());
	            }
	            buffer.clear(); // do something with the data and clear/compact it.
	        }
	        inChannel.close();
	        aFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
