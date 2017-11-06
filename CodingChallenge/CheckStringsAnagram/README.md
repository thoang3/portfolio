# thoang3

Name: Tung Hoang

Email: thoang3@uic.edu

Problem: Determine if given two strings are anagram.

Approach: Use hash map to store (character, occurrence) pair in string 1. Then update the hash map by decrement character in string 2 that also occurs in string 1. If two strings are anagram, the updated values in hash map should all be zeros. Time Complexity: O(n)