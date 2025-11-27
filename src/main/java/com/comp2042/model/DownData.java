package com.comp2042.model;

/**
 * This class just bundles two things together when the brick moves down the cleared row info (if got clear lah)
 */
public final class DownData {

    private final ClearRow clearRow;
    private final ViewData viewData;

    /**
     * Create a DownData object.
     * @param clearRow info about how many lines cleared (or null if nothing lah)
     * @param viewData updated brick view so GUI can refresh properly
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * Get the clear row data.
     * @return ClearRow object, or null if no line kena clear.
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Get the updated view data for the brick.
     * @return ViewData used by GUI to redraw.
     */
    public ViewData getViewData() {
        return viewData;
    }
}
