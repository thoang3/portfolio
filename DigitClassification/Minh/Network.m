function [ weight, bias ] = Network(input, target, nodeLayers, numEpochs, batchSize, eta)

%random initiation

weights = {};
bias = {};
for layer = 2:length(nodeLayers)
weights{layer} = normrnd(0,1,nodeLayers(layer), nodeLayers(layer-1)); %weight transpose already
bias{layer} = normrnd(0,1,nodeLayers(layer), 1);   
end

%epochs (e.g = 20)

%batch size (e.g = 2) 

%counter = 1; %for epochs
n_batch = floor(length(input)/batchSize); %number of batch 4/2 = 2 , 8/3 = 2 => n_batch = 3
if n_batch*batchSize == length(input) %2*2 = 4
        n_batch = n_batch;
else
        n_batch = n_batch + 1; 
end

%feed forward

for epoch = 1:numEpochs
    %Permutation at each epoch
    id = randperm(length(input));
    input = input(id,:);
    target = target(id,:);
    
    %batch size
    batch_round = {};
    real_result = {};
    
    for b_round = (1:(n_batch - 1)) % e.g. 2 and 3
        batch_round{b_round} = input(((b_round*2) - 1):b_round*batchSize,:);       %input
        real_result{b_round} = target(((b_round*2) - 1):b_round*batchSize,:);      %target
    end
    
    batch_round{n_batch} = input((batchSize*(n_batch - 1) + 1):length(input),:);       %input
    real_result{n_batch} = target((batchSize*(n_batch - 1) + 1):length(input),:);      %target 
    
    
    for batch = 1:length(batch_round)
        sum_output = {}; % w * input + bias
        deri_output = {}; % derivation of output aka sigmoid prime
        output = {}; % sum_output through sigmoid = output
        output{1} = batch_round{batch}';  %row = instance, column = feature %xor case input comes by row
        delta = {};
        %target = {};
        %target(length(nodeLayers)) = 0;
        
        %feed forward
        for layer = 2:length(nodeLayers)
            sum_output{layer} = bsxfun(@plus,(weights{layer}*output{layer - 1}), bias{layer});  %!!!bias dimension
            output{layer} = 1./(1 + exp(-sum_output{layer})); %Sigmoid(sum_output{layer})
            deri_output{layer} =  output{layer}.*(1-output{layer});%DSigmoid(sum_output{layer});
            %fprintf('%i\n', output{layer});
        end
        
        %error rate

    	error = (output{length(nodeLayers)}) -  (real_result{batch}'); %!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        delta{length(nodeLayers)} = error .* deri_output{length(nodeLayers)};	

        %backward pass

        for layer = (length(nodeLayers)-1) : -1 : 2
            delta{layer} = ((weights{layer+1})' * delta{layer+1}) .* deri_output{layer};
        end

        % output gradient
        for layer = length(nodeLayers) : -1 : 2
            weights{layer} = weights{layer} - eta/length(batch_round{batch}) * delta{layer} * output{layer-1}';
            bias{layer} = bias{layer} - eta/length(batch_round{batch}) * sum(delta{layer}, 2);
        end
    
    end
    %MSE for each epochs
    output_mse = {};
    sum_output_mse = {};
    output_mse{1} = input';
    for layer = 2 : length(nodeLayers)
          sum_output_mse{layer} = bsxfun(@plus,(weights{layer}*output_mse{layer - 1}), bias{layer});  %!!!bias dimension
          output_mse{layer} = 1./(1 + exp(-sum_output_mse{layer})); %Sigmoid(sum_output{layer})
    end
    
    MSE = 1/(2*(length(input))) * sum(sum((.5 * (target' - output_mse{length(nodeLayers)}).^2)));
    test = immse(target', output_mse{length(nodeLayers)});
    fprintf('%i\n', MSE, output_mse{length(nodeLayers)});
end

weight = weights; 
bias = bias;

end
