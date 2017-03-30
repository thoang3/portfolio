%% Activation Function 1 Derivative
function phi1Deri = ActivationFunction1Deri(v) 
        phi1Deri = 1 - (ActivationFunction1(v).^2); 
        %phi1Deri = ActivationFunction1(v) .* (1-ActivationFunction1(v));
end