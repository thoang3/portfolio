%%
clc
close all
clear all

% number of data sample
n = 300;

% data input vector
x = zeros(n, 1);

for i = 1:n
    x(i) = 2 * rand(1) -1;
end

nu = zeros(n, 1);

for i = 1:n
    nu(i) = 2/10 * rand(1) - 1/10
end

d = zeros(n, 1);

for i = 1:n
    d(i) = sin(20 * x(i)) + 3 * x(i) + nu(i);
end

figure(1)
hold on
plot(x, d, 'or')
%hold off

title('Plot of 300 points (x_i, d_i)')
xlabel('x value')
ylabel('d value')
%Set PaperPositionMode to auto so that the exported figure looks like it does on the screen.
set(gcf, 'PaperPositionMode', 'auto');
print('-depsc2', strcat('Plot 300 points', '-', date, '.eps'));


%%

% number of input, hidden, and output neurons
M = 1; N = 24; L = 1;

max_epochs = 50; % # of maximum epoch
threshold = 0.1; % mean-squared error threshold
eta = 0.01; % learning rate


% Initialize weight and bias
%W1 = rand(N, M+1);
%W2 = rand(L, N+1);
W1 = 2.*rand(N, M+1) -1;
W2 = 2.*rand(L, N+1) -1;

%W1(:,1) = 0;
%W2(:,1) = 0;

% Mean-squared error vector for respective epoch
mse = zeros(max_epochs, 1);

y = zeros(n, 1);

% previous mse, rescale eta to a smaller if mse is increasing
previous_mse = 1;

% Begin training
for iter = 1:max_epochs
    for i = 1:n
        [W1, W2] = backPropagation(eta, x(i), d(i), W1, W2);  
    end
    
    %plot overall network error at end of each iteration
    error = zeros(n, L);
    
    for i = 1:n
        [v1, y1, v2, y2] = feedForwardNet(x(i), W1, W2);
        error(i,:) = d(i) - y2;
        y(i) = y2;   
    end
    
    mse(iter) = (sum(error.^2))/n;
    if (mse(iter) >= previous_mse)
       eta = eta * 0.5  
    end
    
    previous_mse = mse(iter)
    
    figure(2);
    plot(mse);
    if mse(iter) < threshold
        break;
    end
    
end


figure(1)
hold on
plot(x, y, 'ob')
hold off

title('Plot of 300 points (x_i, d_i) and its fit')
xlabel('x value')
ylabel('d value')
%Set PaperPositionMode to auto so that the exported figure looks like it does on the screen.
set(gcf, 'PaperPositionMode', 'auto');
print('-depsc2', strcat('Plot 300 points and its fit', '-', date, '.eps'));

% Plot of #epoch and MSE for training case
figure(2)
plot(mse)
title('Epochs vs MSE for training dataset')
xlabel('#epoch')
ylabel('MSE')
%Set PaperPositionMode to auto so that the exported figure looks like it does on the screen.
set(gcf, 'PaperPositionMode', 'auto');
print('-depsc2', strcat('Epochs vs mse', '-', date, '.eps'));

