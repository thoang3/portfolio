function [Tm] = evenScaleVector(Tn,M)
% Function to even scale number serie Tn (length N) to Tm (length M, N<M)
% Inputs: numberical serie: Tn, length to be scaled: M
% Output: even scaled numberical series of length M
%
% Changchuan Yin
% University of Illinois at Chicago
% Email: cyinbox@gmail.com
% Last update 06/18/2015
%
% Citation:
% Hoang,T., Yin, C., & Yau, S. S. T. (2016). Numerical encoding of DNA sequences by Chaos Game Representation
% with application in similarity comparison. Genomics, Vol 107, 2016, Elsevier Inc. Journal of Theoretical Biology.

 N = length(Tn);
 Tm = zeros(1,M);
 Tm(1) = Tn(1);
 
 for k = 2:M
   Q = k*N/M;
   R = floor(k*N/M);
  
   if R == 0
      R = 1;
   end
    
   if Q == R  %Q is an integer  
      Tm(k) = Tn(Q);
   else       %Q is not an integer  
      Tm(k) = Tn(R) + (Q-R)*(Tn(R+1)-Tn(R)); 
   end

end

