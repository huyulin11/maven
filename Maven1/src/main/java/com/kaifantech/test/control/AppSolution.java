package com.kaifantech.test.control;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class AppSolution {
	public static final void main(String[] args) {
		testMedianSortedArrays();
	}

	protected static void testMedianSortedArrays() {
		int[] nums1 = new int[] { 1, 2, 5, 7, 8 }, nums2 = new int[] { 4, 6, 8, 9 };
		findMedianSortedArrays(nums1, nums2);
	}

	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int[] nums3 = Arrays.copyOf(nums1, nums1.length + nums2.length);
		for (int num : nums2) {
		}
		int flag = 0;
		return 0;
	}

	protected static void testQueue() {
		Queue<Integer> q = new LinkedBlockingQueue<>();
		q.add(1);
		q.add(5);
		q.add(7);
		q.add(8);
		System.out.println(q.remove());
	}

	protected static void testNode() {
		ListNode a = makeListNode(5);
		ListNode b = makeListNode(5);
		ListNode c = addTwoNumbers(a, b);
		System.out.println(c);
	}

	public static ListNode makeListNode(int... aa) {
		HashMap<Integer, ListNode> map = new HashMap<>();
		for (int i = 0; i < aa.length; i++) {
			ListNode a = new ListNode(aa[i]);
			map.put(i, a);
			if (i > 0)
				map.get(i - 1).next = a;
		}
		return map.get(0);
	}

	public static ListNode addTwoNumbers(ListNode node1, ListNode node2) {
		ListNode loopNode1 = node1, loopNode2 = node2, root = null, current = null;
		boolean up = false;
		while (loopNode1 != null || loopNode2 != null || up) {
			int val = 0;
			if (loopNode1 != null) {
				val += loopNode1.val;
				loopNode1 = loopNode1.next;
			}
			if (loopNode2 != null) {
				val += loopNode2.val;
				loopNode2 = loopNode2.next;
			}
			val += up ? 1 : 0;
			if (root == null) {
				root = new ListNode(val % 10);
				current = root;
			} else {
				current.next = new ListNode(val % 10);
				current = current.next;
			}
			if (val >= 10)
				up = true;
			else
				up = false;
		}
		return root;
	}
}

class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}
}
