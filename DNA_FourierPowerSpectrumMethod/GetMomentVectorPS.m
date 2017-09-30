function [v] = GetMomentVectorPS(seq)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here

n=length(seq);

%Binary indicator sequences of A, C, G, T (each value will be either 1 or 0
%; 1 if that nucleotide appears in that position and 0 otherwise)
uA=zeros(1, n);
uC=zeros(1, n);
uG=zeros(1, n);
uT=zeros(1, n);

%Sequences' length (of A, C, G, T respectively)
nA=0;
nC=0;
nG=0;
nT=0;

%Get the binary indicator sequences and their lengths
for i=1:n
    nu=seq(i);
   switch upper(nu)
      case {'A'}
           uA(i)=1;
           uC(i)=0;
           uG(i)=0;
           uT(i)=0;
           nA=nA+1;
      case {'C'}
           uC(i)=1;
           uA(i)=0;
           uG(i)=0;
           uT(i)=0;
           nC=nC+1;     
      case {'G'}
           uG(i)=1;
           uA(i)=0;
           uC(i)=0;
           uT(i)=0;
           nG=nG+1;
      case {'T'}
           uT(i)=1;
           uA(i)=0;
           uC(i)=0;
           uG(i)=0;
           nT=nT+1;
   end
end

%Discrete Fourier Transforms
UA=fft(uA);   
UC=fft(uC);
UG=fft(uG);
UT=fft(uT);

%Exclude the first term
UA(1)=[];
UC(1)=[];
UG(1)=[];
UT(1)=[];

% Get the first half of the transform (since it's symmetric)
m=floor((n-1)/2);
UA1=UA(1:m);
UC1=UC(1:m);
UG1=UG(1:m);
UT1=UT(1:m);

%Power spectrums
PSA=abs(UA).^2;     
PSC=abs(UC).^2;     
PSG=abs(UG).^2;     
PST=abs(UT).^2;     

%Normalized moments
MA=zeros(1,3);   
MC=zeros(1,3);
MG=zeros(1,3);
MT=zeros(1,3);

%Moment vector
for j=1:3
   MA(j)=(nA*(n-nA))*sum(PSA.^j)/(nA^(j)*(n-nA)^(j)); 
   MC(j)=(nC*(n-nC))*sum(PSC.^j)/(nC^(j)*(n-nC)^(j)); 
   MG(j)=(nG*(n-nG))*sum(PSG.^j)/(nG^(j)*(n-nG)^(j)); 
   MT(j)=(nT*(n-nT))*sum(PST.^j)/(nT^(j)*(n-nT)^(j)); 
end


v=[MA(1), MC(1), MG(1), MT(1), MA(2), MC(2), MG(2), MT(2), MA(3), MC(3), MG(3), MT(3)];


end

