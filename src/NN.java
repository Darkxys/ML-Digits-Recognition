import java.util.ArrayList;
import java.util.List;

public class NN {
    private final int[] NEURON_QUANTITY = {16, 16, 10};
    private final int IMAGE_WIDTH = 28;
    private final float LEARNING_RATE = 0.1f;

    private List<Neuron[]> network = new ArrayList<Neuron[]>();

    public void runNN() {
        float[][] images = normalizeImages(MNISTReader.readMNISTImages("data/train-images.idx3-ubyte"));
        byte[] labels = MNISTReader.readMNISTLabels("data/train-labels.idx1-ubyte");
    }

    public float[] forwardPropagate(float[] row) {
        float[] inputs = row;
        for (Neuron[] layer : network) {
            for (int i = 0; i < layer.length; i++) {
                float activation = activate(layer[i], inputs);
                layer[i].activation = transfer(activation);
                inputs[i] = layer[i].activation;
            }
        }
        return inputs;
    }

    public float[][] normalizeImages(float[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] /= 127;
            }
        }
        return data;
    }

    public float activate(Neuron n, float[] inputs) {
        float activation = n.weights[0];
        for (int i = 1; i < n.weights.length; i++) activation += n.weights[i] * inputs[i];
        return activation;
    }

    public float transfer(float activation) {
        return Math.max(0, activation);
    }

    public float transferDerivative(float activation) {
        return (activation <= 0 ? 0 : 1);
    }

    public void initNetwork() {
        for (int i = 0; i < NEURON_QUANTITY.length; i++) {
            network.add(initLayer(NEURON_QUANTITY[i], (i == 0 ? IMAGE_WIDTH * IMAGE_WIDTH : NEURON_QUANTITY[i - 1])));
        }
    }

    public Neuron[] initLayer(int quantity, int quantityPreviousLayer) {
        Neuron[] tmp = new Neuron[quantity];
        for (int i = 0; i < quantity; i++) tmp[i] = new Neuron(initWeights(quantityPreviousLayer));
        return tmp;
    }

    public float[] initWeights(int quantity) {
        float[] tmp = new float[quantity];
        for (int i = 0; i < quantity; i++) tmp[i] = (float) (Math.random() - 0.5) * 20;
        return tmp;
    }
}
