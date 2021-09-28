public class Neuron {
    public float[] weights;
    public float delta;
    public float activation;

    public Neuron(float[] w){
        weights = w;
        delta = 0;
        activation = 0;
    }
}
