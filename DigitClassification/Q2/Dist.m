function [ dist ] = Dist( x, y )
% Distance function between desired output and network output in question 2
%   Detailed explanation goes here
    dist = 0;
    for i = 1: length(x)
        dist = dist + (x(i) - y(i))^2;
    end
    

end

