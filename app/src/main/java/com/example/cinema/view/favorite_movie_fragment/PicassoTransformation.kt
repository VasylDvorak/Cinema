package com.example.cinema.view.favorite_movie_fragment

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.squareup.picasso.Transformation

class PicassoTransformation : Transformation {
    override fun transform (source: Bitmap): Bitmap {
        val path = Path()
        path.addCircle(
            (source.width / 2).toFloat(),
            (source.height / 2).toFloat(),
            (source.width / 2).toFloat(),
            Path.Direction.CW)

        val answerBitMap =
            Bitmap.createBitmap(source.width, source.height,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(answerBitMap)
        canvas.clipPath(path)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawBitmap(source, 0f, 0f, paint)
        source.recycle()
        return answerBitMap
    }
    override fun key (): String {
        return "circle"
    }
}
