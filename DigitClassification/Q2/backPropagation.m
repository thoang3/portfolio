%% BackPropagate: Backpropagate the output through the network and adjust weights and biases
function [W1, W2] = backPropagation(eta, input, d, W1, W2)

    [v1, y1, v2, y2] = feedForwardNet(input, W1, W2);
    
    delta2 = (d - y2) .* ActivationFunction2Deri(v2);
    Deriv2 = - delta2 * [1; y1]';
    [r, c] = size(W2);
    V = W2(1:r, 2:c);
    delta1 = V' * delta2 .* ActivationFunction1Deri(v1);
    Deriv1 = - delta1 * [1; input]';

    % update weights
    W1 =   W1 - eta * Deriv1;
    W2 =   W2 - eta * Deriv2;
    
end