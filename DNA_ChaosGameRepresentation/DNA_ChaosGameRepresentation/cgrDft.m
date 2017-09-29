%% Compute power spectrum of a DNA sequence via discrete Fourier transform and Chaos Game Representation
%  
%
% Input:  DNA sequence
% Output: Power spectrum of that DNA sequence
%
% Tung Hoang, Ph.D candidate
% Dept. of Mathematics, Statistics and Computer Science
% University of Illinois at Chicago, Chicago IL, USA
% Last update 05/15/2016
%
% Citation:
% Hoang,T., Yin, C., & Yau, S. S. T. (2016). Numerical encoding of DNA sequences by Chaos Game Representation
% with application in similarity comparison. Genomics, Vol 107, 2016, Elsevier Inc.

function [ PSZ ] = cgrDft( seq )

len = length(seq);

x(1) = 0.0; y(1) = 0.0;
z(1) = x(1) + 1i * y(1);

for j = 1:len
    
    a = seq(j);
    
    switch a
    
    case 'A'

            v = [0,0];
    case 'C'

            v = [0,1];
    case 'G'

            v = [1,0];
    case 'T'

            v = [1,1];
    end
    
    x(j+1) = 0.5 * (x(j) + v(1));
    y(j+1) = 0.5 * (y(j) + v(2));
    z(j+1) = x(j+1) + 1i * y(j+1);  
end
%[x;y]'

% figure;
% plot(x,y);
% scatter(x,y,'.');
% xlim([0,1]);
% ylim([0,1]);


%Discrete Fourier Transforms
Z = fft(z);

%Power spectrums
PSZ = abs(Z).^2;
PSZ(1)=[]; %Exclude the first term

%figure;
%plot(PSZ);
end

