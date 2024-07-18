
public class NeuralNetwork {
	public int inputs;
	public int hidden1;
	public int hidden2;
	public int outputs;

	public Matrix weightsIH1;
	public Matrix weightsH1H2;
	public Matrix weightsH2O;

	public Matrix BiasIH1;
	public Matrix BiasH1H2;
	public Matrix BiasH2O;

	public NeuralNetwork(int i, int h1, int h2, int o) {
		inputs = i;
		hidden1 = h1;
		hidden2 = h2;
		outputs = o;

		weightsIH1 = new Matrix(hidden1, inputs); // rows and columns are inverted due to matrix multiplication rules
		weightsH1H2 = new Matrix(hidden2, hidden1);
		weightsH2O = new Matrix(outputs, hidden2);

		BiasIH1 = new Matrix(hidden1, 1); // basically a column matrix
		BiasH1H2 = new Matrix(hidden2, 1); // "
		BiasH2O = new Matrix(outputs, 1); // "
	}

	public double[] makeGuess(double[] senses) {
		Matrix inputs = Matrix.ConvertArrayToMatrix(senses);

		Matrix Hidden1 = Matrix.MultiplyThese(weightsIH1, inputs);
		Hidden1.add(BiasIH1);
		Hidden1.applySigmoidal();

		Matrix Hidden2 = Matrix.MultiplyThese(weightsH1H2, Hidden1);
		Hidden2.add(BiasH1H2);
		Hidden2.applySigmoidal();

		Matrix Output = Matrix.MultiplyThese(weightsH2O, Hidden2);
		Output.add(BiasH2O);
		// Output.applySigmoidal(); can toggle between including or not

		return Matrix.ConvertMatrixToArray(Output);
	}

	// Methods created by Murray
	public NeuralNetwork copy() {
		NeuralNetwork copy = new NeuralNetwork(inputs, hidden1, hidden2, outputs);
		copy.weightsIH1 = Matrix.CopyOf(weightsIH1);
		copy.weightsH1H2 = Matrix.CopyOf(weightsH1H2);
		copy.weightsH2O = Matrix.CopyOf(weightsH2O);
		copy.BiasIH1 = Matrix.CopyOf(BiasIH1);
		copy.BiasH1H2 = Matrix.CopyOf(BiasH1H2);
		copy.BiasH2O = Matrix.CopyOf(BiasH2O);

		return copy;
	}

	public NeuralNetwork mutatedcopy() {
		NeuralNetwork mcopy = new NeuralNetwork(inputs, hidden1, hidden2, outputs);
		mcopy.weightsIH1 = Matrix.CopyOf(weightsIH1);
		mcopy.weightsH1H2 = Matrix.CopyOf(weightsH1H2);
		mcopy.weightsH2O = Matrix.CopyOf(weightsH2O);
		mcopy.BiasIH1 = Matrix.CopyOf(BiasIH1);
		mcopy.BiasH1H2 = Matrix.CopyOf(BiasH1H2);
		mcopy.BiasH2O = Matrix.CopyOf(BiasH2O);

		mcopy.weightsIH1.mutateElements();
		mcopy.weightsH1H2.mutateElements();
		mcopy.weightsH2O.mutateElements();
		mcopy.BiasIH1.mutateElements();
		mcopy.BiasH1H2.mutateElements();
		mcopy.BiasH2O.mutateElements();

		return mcopy;
	}

	public NeuralNetwork averagewith(NeuralNetwork nn) {
		NeuralNetwork avg = new NeuralNetwork(inputs, hidden1, hidden2, outputs);
		avg.weightsIH1 = Matrix.AverageThese(weightsIH1, nn.weightsIH1);
		avg.weightsH1H2 = Matrix.AverageThese(weightsH1H2, nn.weightsH1H2);
		avg.weightsH2O = Matrix.AverageThese(weightsH2O, nn.weightsH2O);
		avg.BiasIH1 = Matrix.AverageThese(BiasIH1, nn.BiasIH1);
		avg.BiasH1H2 = Matrix.AverageThese(BiasH1H2, nn.BiasH1H2);
		avg.BiasH2O = Matrix.AverageThese(BiasH2O, nn.BiasH2O);

		avg.weightsIH1.mutateElements();
		avg.weightsH1H2.mutateElements();
		avg.weightsH2O.mutateElements();
		avg.BiasIH1.mutateElements();
		avg.BiasH1H2.mutateElements();
		avg.BiasH2O.mutateElements();

		return avg;
	}

	public NeuralNetwork combinewith(NeuralNetwork nn) {
		NeuralNetwork comb = new NeuralNetwork(inputs, hidden1, hidden2, outputs);
		comb.weightsIH1 = Matrix.CombineThese(weightsIH1, nn.weightsIH1);
		comb.weightsH1H2 = Matrix.CombineThese(weightsH1H2, nn.weightsH1H2);
		comb.weightsH2O = Matrix.CombineThese(weightsH2O, nn.weightsH2O);
		comb.BiasIH1 = Matrix.CombineThese(BiasIH1, nn.BiasIH1);
		comb.BiasH1H2 = Matrix.CombineThese(BiasH1H2, nn.BiasH1H2);
		comb.BiasH2O = Matrix.CombineThese(BiasH2O, nn.BiasH2O);

		comb.weightsIH1.mutateElements();
		comb.weightsH1H2.mutateElements();
		comb.weightsH2O.mutateElements();
		comb.BiasIH1.mutateElements();
		comb.BiasH1H2.mutateElements();
		comb.BiasH2O.mutateElements();

		return comb;
	}
}