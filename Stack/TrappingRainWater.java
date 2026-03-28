import java.util.Stack;

public class TrappingRainWater {
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int totalWater = 0;
        int current = 0;

        while (current < height.length) {
            // While current height is taller than the top of the stack,
            // we have found a "Right Wall" for a potential container.
            while (!stack.isEmpty() && height[current] > height[stack.peek()]) {
                
                int mid = stack.pop(); // This is the "Bottom" of the pit
                
                // If stack is empty, there is no "Left Wall", so no water can be trapped
                if (stack.isEmpty()) {
                    break;
                }
                
                int leftWall = stack.peek(); // The index of the "Left Wall"
                
                // Calculate the horizontal distance (width)
                int width = current - leftWall - 1;
                
                // Calculate the vertical water level (height)
                // It's the limiting wall minus the bottom height
                int boundedHeight = Math.min(height[current], height[leftWall]) - height[mid];
                
                totalWater += width * boundedHeight;
            }
            
            // Push current index and move to the next bar
            stack.push(current++);
        }
        
        return totalWater;
    }
}