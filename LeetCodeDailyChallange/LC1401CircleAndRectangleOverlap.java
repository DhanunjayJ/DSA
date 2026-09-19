class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {
        // Find the single point (xn, yn) on or inside the rectangle closest to the circle center.
        // Since the rectangle is axis-aligned, X and Y dimensions are completely independent.
        // We clamp xCenter to [x1, x2] and yCenter to [y1, y2]:
        //
        // 1. Inside the rectangle:
        //    Neither coordinate clamps -> (xn, yn) = (xCenter, yCenter), distance is 0.
        // 2. Along edges (top, bottom, left, right):
        //    Only one coordinate clamps -> snaps directly to the nearest edge point.
        // 3. Diagonal regions:
        //    Both coordinates clamp -> snaps directly to the nearest corner.
        
        int xn = Math.max(x1, Math.min(xCenter, x2));
        int yn = Math.max(y1, Math.min(yCenter, y2));
        
        // Calculate the vector components from the circle's center to this closest point
        int dx = xn - xCenter;
        int dy = yn - yCenter;
        
        // Check if the closest point lies within the circle:
        // dist^2 <= radius^2 (avoids Math.sqrt to prevent floating-point precision loss and extra overhead)
        return dx * dx + dy * dy <= radius * radius;
    }
}

// explaination - https://lnkd.in/p/dfFpgZ5A