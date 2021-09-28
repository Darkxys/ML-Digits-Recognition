import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MNISTReader {
    public final static int IMAGE_SIZE = 784;

    public static byte[] readMNISTLabels(String path) {
        File file = new File(path);

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            raf.skipBytes(1 * Integer.BYTES);

            int quantity = raf.readInt();
            byte[] data = new byte[quantity];
            raf.readFully(data);

            return data;
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static float[][] readMNISTImages(String path) {
        File file = new File(path);

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            raf.skipBytes(1 * Integer.BYTES);
            int quantity = raf.readInt();
            byte[] tmp = new byte[quantity*IMAGE_SIZE];
            float[][] data = new float[quantity][IMAGE_SIZE];
            raf.skipBytes(2 * Integer.BYTES);
            raf.readFully(tmp);

            for (int i = 0; i < quantity; i++) {
                for (int k = 0; k < IMAGE_SIZE; k++) {
                    data[i][k] = tmp[i * IMAGE_SIZE + k];
                }
            }

            return data;
        } catch (IOException e) {
            return new float[0][0];
        }
    }
}
