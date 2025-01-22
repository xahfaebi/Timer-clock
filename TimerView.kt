class TimerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 1f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        strokeCap = Paint.Cap.ROUND
        color = Color.BLUE
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = Color.LTGRAY
    }

    fun setProgress(progress: Float) {
        this.progress = progress.coerceIn(0f, 1f)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (Math.min(width, height) / 2f) - paint.strokeWidth

        // Draw background circle
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

        // Draw progress arc
        val rect = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
        
        canvas.drawArc(
            rect,
            -90f,
            360f * progress,
            false,
            paint
        )
    }
}
