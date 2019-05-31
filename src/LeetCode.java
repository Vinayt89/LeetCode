import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class LeetCode {

	public static void main(String[] args) {
		// letterCombinations("234").forEach(System.out::println);
		// System.out.println(lengthOfLastWord("Hello World"));
		System.out.println(singleNumber(new int[] { 4, 1, 2, 1, 2 }));
	}

	public static boolean isMatch(String s, String p) {
		if (p.length() == 0) {
			return s.length() == 0;
		}
		if (p.length() > 1 && p.charAt(1) == '*') { // second char is '*'
			String ss = p.substring(2);
			if (isMatch(s, ss)) {
				return true;
			}
			if (s.length() > 0 && (p.charAt(0) == '.' || s.charAt(0) == p.charAt(0))) {
				return isMatch(s.substring(1), p);
			}
			return false;
		} else { // second char is not '*'
			if (s.length() > 0 && (p.charAt(0) == '.' || s.charAt(0) == p.charAt(0))) {
				return isMatch(s.substring(1), p.substring(1));
			}
			return false;
		}
	}

	/**
	 * https://leetcode.com/problems/search-insert-position/
	 */
	public static int searchInsert(int[] nums, int target) {
		int start = 0;
		int end = nums.length - 1;
		while (start <= end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] < target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return start;
	}

	/**
	 * https://leetcode.com/problems/substring-with-concatenation-of-all-words/
	 */
	public static List<Integer> findSubstring(String s, String[] words) {
		// barfoothefoobarman
		// foo bar

		final Map<String, Integer> counts = new HashMap<>();
		for (final String word : words) {
			counts.put(word, counts.getOrDefault(word, 0) + 1);
		}
		final List<Integer> indexes = new ArrayList<>();
		final int n = s.length(), num = words.length, len = words[0].length();
		int hmm = n - num * len + 1;
		for (int i = 0; i < hmm; i++) {
			final Map<String, Integer> seen = new HashMap<>();
			int j = 0;
			while (j < num) {
				final String word = s.substring(i + j * len, i + (j + 1) * len);
				if (counts.containsKey(word)) {
					seen.put(word, seen.getOrDefault(word, 0) + 1);
					if (seen.get(word) > counts.getOrDefault(word, 0)) {
						break;
					}
				} else {
					break;
				}
				j++;
			}
			if (j == num) {
				indexes.add(i);
			}
		}
		return indexes;
	}

	/**
	 * https://leetcode.com/problems/string-to-integer-atoi/
	 */
	public static int myAtoi(String str) {
		if (str == null || str.length() == 0)
			return 0;
		str = str.trim();
		char firstChar = str.charAt(0);
		int sign = 1, start = 0, len = str.length();
		long sum = 0;
		if (firstChar == '+') {
			sign = 1;
			start++;
		} else if (firstChar == '-') {
			sign = -1;
			start++;
		}
		for (int i = start; i < len; i++) {
			if (!Character.isDigit(str.charAt(i)))
				return (int) sum * sign;
			sum = sum * 10 + str.charAt(i) - '0';
			if (sign == 1 && sum > Integer.MAX_VALUE)
				return Integer.MAX_VALUE;
			if (sign == -1 && (-1) * sum < Integer.MIN_VALUE)
				return Integer.MIN_VALUE;
		}

		return (int) sum * sign;
	}

	/**
	 * https://leetcode.com/problems/combination-sum/
	 */
	public static List<List<Integer>> combinationSum(int[] candidates, int target) {
		List<List<Integer>> list = new ArrayList<>();
		Arrays.sort(candidates);
		backtrack(list, new ArrayList<>(), candidates, target, 0);
		return list;
	}

	private static void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int remain, int start) {
		if (remain < 0)
			return;
		else if (remain == 0)
			list.add(new ArrayList<>(tempList));
		else {
			for (int i = start; i < nums.length; i++) {
				tempList.add(nums[i]);
				int newRemain = remain - nums[i];
				backtrack(list, tempList, nums, newRemain, i); // not i + 1
																// because we
																// can reuse
																// same elements
				tempList.remove(tempList.size() - 1);
			}
		}
	}

	/**
	 * https://leetcode.com/problems/roman-to-integer/
	 */
	private static int romanToInt(String s) {
		int sum = 0;
		int prev = 0, cur = 0;

		for (char ch : s.toCharArray()) {
			switch (ch) {
			case 'I':
				cur = 1;
				break;
			case 'V':
				cur = 5;
				break;
			case 'X':
				cur = 10;
				break;
			case 'L':
				cur = 50;
				break;
			case 'C':
				cur = 100;
				break;
			case 'D':
				cur = 500;
				break;
			case 'M':
				cur = 1000;
				break;
			default:
				cur = 0;
			}

			if (prev == 0)
				prev = cur;
			else if (prev < cur) {
				sum += cur - prev;
				prev = 0;
			} else if (prev == cur) {
				sum += cur;
			} else if (cur < prev) {
				sum += prev;
				prev = cur;
			}
		}
		if (prev > 0)
			sum += prev;
		return sum;
	}

	/**
	 * https://leetcode.com/problems/longest-common-prefix/
	 */
	private static String longestCommonPrefix(String[] strs) {
		if (strs.length == 0) {
			return "";
		}
		for (int i = 0; i < strs[0].length(); i++) {
			char ch = strs[0].charAt(i);
			for (int j = 1; j < strs.length; j++) {
				if (i >= strs[j].length() || strs[j].charAt(i) != ch) {
					return strs[0].substring(0, i);
				}
			}
		}
		return strs[0];
	}

	/**
	 * https://leetcode.com/problems/count-and-say/
	 */
	private static String countAndSay(int n) {
		if (n == 1)
			return "1";
		String prev = countAndSay(n - 1);
		StringBuilder sb = new StringBuilder();
		int count = 1;
		for (int i = 0; i < prev.length(); i++) {
			if (i < prev.length() - 1 && prev.charAt(i) == prev.charAt(i + 1)) {
				count++;
			} else {
				sb.append(count).append(prev.charAt(i));
				count = 1;
			}
		}
		return sb.toString();
	}

	/**
	 * https://leetcode.com/problems/missing-number/
	 */
	private static int missingNumber(int[] nums) {
		int max = nums.length, sum = 0;
		for (int i = 0; i < nums.length; i++)
			sum += nums[i];
		return ((max * (max + 1)) / 2) - sum;
	}

	/**
	 * https://leetcode.com/problems/reverse-string/
	 */
	private static void reverseString(char[] s) {
		for (int i = 0; i < Math.round(s.length / 2); i++) {
			char c = s[i];
			s[i] = s[s.length - i - 1];
			s[s.length - i - 1] = c;
		}
	}

	/**
	 * https://leetcode.com/problems/fizz-buzz/
	 */
	private static List<String> fizzBuzz(int n) {
		List<String> resList = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			if (i % 3 == 0 && i % 5 == 0) {
				resList.add("FizzBuzz");
			} else if (i % 3 == 0) {
				resList.add("Fizz");
			} else if (i % 5 == 0) {
				resList.add("Buzz");
			} else {
				resList.add(String.valueOf(i));
			}
		}
		return resList;
	}

	/**
	 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
	 */
	private static List<String> letterCombinations(String digits) {

		if (digits == null || digits.isEmpty()) {
			return Collections.emptyList();
		}
		List<String> list = new LinkedList<>();
		StringBuilder sb = new StringBuilder();
		dfs(list, digits, sb, 0);
		return list;
	}

	private static void dfs(List<String> list, String digits, StringBuilder sb, int idx) {
		Map<Character, String> phNo = new HashMap<>();
		phNo.put('2', "abc");
		phNo.put('3', "def");
		phNo.put('4', "ghi");
		phNo.put('5', "jkl");
		phNo.put('6', "mno");
		phNo.put('7', "pqrs");
		phNo.put('8', "tuv");
		phNo.put('9', "wxyz");

		if (idx == digits.length()) {
			list.add(sb.toString());
			return;
		}
		char n = digits.charAt(idx);
		if (n == '1' || n == '0') {
			idx++;
		}
		char[] chArr = phNo.get(digits.charAt(idx)).toCharArray();
		for (char c : chArr) {
			sb.append(c);
			dfs(list, digits, sb, idx + 1);
			sb.setLength(sb.length() - 1);
		}
	}

	private static int lengthOfLastWord(String s) {
		if (s == null || s.length() < 1) {
			return 0;
		}
		if (s.indexOf(' ') == -1) {
			return s.length();
		}
		s = s.trim();
		for (int i = s.length() - 1; i > -1; i--) {
			if (s.charAt(i) == ' ') {
				return s.length() - i - 1;
			}
		}
		return s.length();
	}

	private static void mergeSortedArray(int[] nums1, int m, int[] nums2, int n) {
		for (int i = m - 1, j = n - 1, k = m + n - 1; k >= 0 && j >= 0;) {
			// when i & j are present and nums2[j] is greater than nums1[i]
			// or i is not present itself.
			if (i < 0 || nums1[i] < nums2[j]) {
				nums1[k--] = nums2[j--];
			} else {
				nums1[k--] = nums1[i--];
			}
		}
		System.out.println(Arrays.toString(nums1));
		return;
	}

	/**
	 * https://leetcode.com/problems/remove-element/submissions/
	 */
	public static int removeElement(int[] nums, int val) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != val) {
				list.add(nums[i]);
			}
		}
		for (int j = 0; j < list.size(); j++) {
			nums[j] = list.get(j);
		}
		return list.size();
	}

	/**
	 * https://leetcode.com/problems/ugly-number/
	 */
	public static boolean isUgly(int num) {
		if (num == 0)
			return false;
		int[] primes = { 2, 3, 5 };
		for (int i : primes) {
			while (num % i == 0) {
				num /= i;
			}
		}
		return num == 1;
	}

	/**
	 * https://leetcode.com/problems/sum-of-two-integers/
	 */
	public static int getSum(int a, int b) {
		while (b != 0) {
			int carry = a & b;
			a = a ^ b;
			b = carry << 1;
		}
		return a;
	}

	/**
	 * https://leetcode.com/problems/next-greater-element-i/
	 */
	public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
		Map<Integer, Integer> map = new HashMap<>();
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < nums2.length; ++i) {
			int num = nums2[i];
			while (!stack.isEmpty() && stack.peek() < num) {
				map.put(stack.pop(), num);
			}
			stack.push(num);
		}
		int[] resArr = new int[nums1.length];
		for (int i = 0; i < nums1.length; ++i) {
			resArr[i] = map.getOrDefault(nums1[i], -1);
		}
		return resArr;
	}

	/**
	 * https://leetcode.com/problems/reverse-string-ii/
	 */
	public static String reverseStr(String s, int k) {
		char[] chArr = s.toCharArray();
		int index = 0;
		while (index < chArr.length) {
			if (index + k - 1 < chArr.length) {
				reverse(chArr, index, index + k - 1);
			} else {
				reverse(chArr, index, chArr.length - 1);
			}
			index += 2 * k;
		}
		return new String(chArr);
	}

	private static void reverse(char[] c, int start, int end) {
		while (start < end) {
			char temp = c[start];
			c[start] = c[end];
			c[end] = temp;
			start++;
			end--;
		}
	}

	/**
	 * https://leetcode.com/problems/reverse-words-in-a-string-iii/
	 */
	public static String reverseWords(String s) {
		String resStr = "";
		String[] strArr = s.split(" ");

		for (int i = 0; i < strArr.length; i++) {
			String currStr = strArr[i];
			String chrStr = "";
			for (int j = currStr.length() - 1; j >= 0; j--) {
				char ch = currStr.charAt(j);
				chrStr += ch;
			}
			resStr += i == strArr.length - 1 ? chrStr : chrStr + " ";
		}
		return resStr;
	}

	/**
	 * https://leetcode.com/problems/maximum-product-of-three-numbers/
	 */
	public static int maximumProduct(int[] nums) {
		Arrays.sort(nums);
		return Math.max(nums[0] * nums[1] * nums[nums.length - 1],
				nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3]);
	}

	/**
	 * https://leetcode.com/problems/single-number/
	 */
	public static int singleNumber(int[] nums) {
		for (int i = 1; i < nums.length; i++) {
			nums[i] = nums[i - 1] ^ nums[i];
		}
		return nums[nums.length - 1];
	}
}
