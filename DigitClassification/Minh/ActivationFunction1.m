%% Activation Function 1
function phi1 = ActivationFunction1(v) 
        phi1 = -1 + 2./(1 + exp(-2*v)); 
        %phi1 = 1./(1+exp(-v));
end