function [v1, y1, v2, y2] = feedForwardNet(input, W1, W2) 
% input is M x 1 vector
% M: number of input neuron, N: number of hidden neuron 
% L: number of ouput neuron
% W1: weight matrix, dim N x (M+1), from input layer to hidden layer, 
% 1st column is bias
% W2: weight matrix, dim L x (N+1), from hidden layer to output layer, 
% 1st column is bias
    v1 = W1 * [1; input];
    y1 = ActivationFunction1(v1);
    v2 = W2 * [1; y1];
    y2 = ActivationFunction2(v2);
end