%% 
clc
close all
clear all

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% INITIAL SETUP
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% load data
trainingImages = loadMNISTImages('train-images.idx3-ubyte');
trainingLabels = loadMNISTLabels('train-labels.idx1-ubyte');
testingImages = loadMNISTImages('t10k-images.idx3-ubyte');
testingLabels = loadMNISTLabels('t10k-labels.idx1-ubyte');

% number of input neuron
M = 784;
% number of hidden neuron
N = 50;
% number of output neuron
L = 10;

% possible desired output (given in form of identity matrix)
iden = eye(10); % d(1) = [1 0 ... 0]' is 0, d(2) is 1, ..., d(10) is 9

% Extract a subset of the training images to test the method
n_train = 60000; % size of training subset, 60000 is full training set
trainingImagesSubset = trainingImages(:, 1:n_train);
trainingLabelsSubset = trainingLabels(1:n_train);

% Extract a subset of the testing images to test the method
n_test = 10000; % size of training subset, 10000 is maximum
testingImagesSubset = testingImages(:, 1:n_test);
testingLabelsSubset = testingLabels(1:n_test);

% input layer, rename to match with backpropagation algorithm and for convenience
x_train = trainingImagesSubset;
x_test = testingImagesSubset; % input layer for testing case

% Match training label to the right form of desired output
d_train = zeros(L, n_train);
for i = 1:n_train
    d_train(:, i) = iden(:, trainingLabelsSubset(i)+1);
end

% Match testing label to the right form of desired output
d_test = zeros(10, n_test);
for i = 1:n_test
    d_test(:, i) = iden(:, testingLabelsSubset(i)+1);
end

max_epochs = 150;  % # of maximum epoch
threshold = 0.05; % energy function threshold
eta = 0.1; % learning rate

% Initialize weights randomly, including biases
W1 = 2.*rand(N, M+1) -1;
W2 = 2.*rand(L, N+1) -1;

% raw form of network output for training case, distance function will be 
% applied to classify into the right (predicted) label
y_train = zeros(L, n_train);
% raw form of network output for testing case, distance function will be 
% applied to classify into the right (predicted) label
y_test = zeros(L, n_test);

% predicted labels for training set
predicted_train = zeros(L, n_train);
% Vector of error count and error rate with respect to epochs for training
trainErrorCountVector = zeros(max_epochs, 1);
trainErrorRateVector = zeros(max_epochs, 1);

% predicted labels for testing set
predicted_test = zeros(L, n_test);
% Vector of error count and error rate with respect to epochs for testing
testErrorCountVector = zeros(max_epochs, 1);
testErrorRateVector = zeros(max_epochs, 1);

% Energy function training vector for respective epoch
energy_train = zeros(max_epochs, 1);
% Energy function testing vector for respective epoch
energy_test = zeros(max_epochs, 1);

% previous energy value, rescale eta to a smaller if energy is increasing
previous_energy = 1;


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% Begin training
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
for epoch = 1:max_epochs
    for i = 1:n_train
        % Backpropagation training
        [W1, W2] = backPropagation(eta, x_train(:, i), d_train(:, i), W1, W2);
    end
    
    % Energy vector for training sample in one epoch
    errorTrain = zeros(n_train, 1);
    
    for i = 1:n_train
        [v1, y1, v2, y2] = feedForwardNet(x_train(:, i), W1, W2);
        errorTrain(i, :) = Dist(d_train(:, i), y2); % energy value at sample i
        y_train(:, i) = y2;  % network output of input sample i
        
    end
    
    % Energy vector for training sample in one epoch
    errorTest = zeros(n_test, 1);
    % compute network output for testing set
    for i = 1:n_test
        [v1, y1, v2, y2] = feedForwardNet(x_test(:, i), W1, W2);
        errorTest(i, :) = Dist(d_test(:, i), y2); % energy value at sample i
        y_test(:, i) = y2;
    end
    
    % count number of training error/ misclassification after one epoch
    trainErrorCount = 0;
    for i = 1:n_train
        v = zeros(L, 1);
        for j = 1:L
            v(j) = Dist(y_train(:, i), iden(:, j));
        end
        [minimum, index] = min(v);
        predicted_train(:, i) = iden(:, index);
        if ~isequal(predicted_train(:, i), d_train(:, i))
            trainErrorCount = trainErrorCount + 1;
        end
    end
    % training error rate after one epoch
    trainErrorRate = 100 * trainErrorCount/n_train;
    % update error count and error rate vector
    trainErrorCountVector(epoch) = trainErrorCount;
    trainErrorRateVector(epoch) = trainErrorRate;
    
    % compute error count and error rate of testing set
    testErrorCount = 0;
    for i = 1:n_test
        v = zeros(L, 1);
        for j = 1:L
            v(j) = Dist(y_test(:, i), iden(:, j));  
        end
        [minimum, index] = min(v);
        predicted_test(:, i) = iden(:, index);
        if ~isequal(predicted_test(:, i), d_test(:, i))
            testErrorCount = testErrorCount + 1;
        end
    end
    % testing error rate after one epoch
    testErrorRate = 100 * testErrorCount/n_test;
    % update error count and error rate vector
    testErrorCountVector(epoch) = testErrorCount;
    testErrorRateVector(epoch) = testErrorRate;
    
    % Energy function is the mean of energy values of all samples
    energy_train(epoch) = sum(errorTrain)/n_train;
    % Energy function is the mean of energy values of all samples
    energy_test(epoch) = sum(errorTest)/n_test;
     
    % rescale eta in case energy is increasing
    if (energy_train(epoch) > previous_energy)
        eta = eta * 0.9;
    end
    previous_energy = energy_train(epoch);
    
    figure(1);
    plot(energy_train);
    if energy_train(epoch) < threshold
        break;
    end
    
end

trainErrorRateVector(max_epochs)
testErrorRateVector(max_epochs)

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% PLOT
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

% Plot of #epoch and energy function for training case
figure(1)
title('Epochs vs Energy Function for training dataset')
xlabel('#epoch')
ylabel('energy')
%Set PaperPositionMode to auto so that the exported figure looks like it does on the screen.
set(gcf, 'PaperPositionMode', 'auto');
print('-depsc2', strcat('Epochs vs Energy for Training', '-', date, '.eps'));

% Plot of #epoch and energy function for testing case
figure(2)
plot(energy_test)
title('Epochs vs Energy Function for testing dataset')
xlabel('#epoch')
ylabel('energy')
%Set PaperPositionMode to auto so that the exported figure looks like it does on the screen.
set(gcf, 'PaperPositionMode', 'auto');
print('-depsc2', strcat('Epochs vs Energy for Testing', '-', date, '.eps'));

% Plot of #epoch and training error rate
figure(3)
plot(trainErrorRateVector);
title('Epochs vs Error Rate for training dataset')
xlabel('#epoch')
ylabel('error rate')
%Set PaperPositionMode to auto so that the exported figure looks like it does on the screen.
set(gcf, 'PaperPositionMode', 'auto');
print('-depsc2', strcat('Epochs vs Error Rate for Training', '-', date, '.eps'));

% Plot of #epoch and training error rate
figure(4)
plot(testErrorRateVector);
title('Epochs vs Error Rate for testing dataset')
xlabel('#epoch')
ylabel('error rate')
%Set PaperPositionMode to auto so that the exported figure looks like it does on the screen.
set(gcf, 'PaperPositionMode', 'auto');
print('-depsc2', strcat('Epochs vs Error Rate for Testing', '-', date, '.eps'));
