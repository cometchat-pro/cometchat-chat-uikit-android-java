package com.cometchat.chatuikit.shared.views.button;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StyleRes;

import com.cometchat.chatuikit.shared.models.BaseStyle;

/**
 * Represents a style configuration for a button, extending the BaseStyle class.
 * <p>
 * This class encapsulates various properties related to the visual appearance of the button.
 */
public class ButtonStyle extends BaseStyle {
    private String buttonTextFont;

    private @ColorInt
    int buttonTextColor;

    private @StyleRes
    int buttonTextAppearance;

    private @ColorInt
    int buttonIconTint, buttonBackgroundColor;

    private @DrawableRes
    int buttonBackgroundDrawable;

    private int width = 0, height = 0;

    @Override
    public ButtonStyle setBackground(int background) {
        super.setBackground(background);
        return this;
    }

    @Override
    public ButtonStyle setBackground(Drawable drawableBackground) {
        super.setBackground(drawableBackground);
        return this;
    }

    @Override
    public ButtonStyle setCornerRadius(float cornerRadius) {
        super.setCornerRadius(cornerRadius);
        return this;
    }

    @Override
    public ButtonStyle setBorderWidth(int borderWidth) {
        super.setBorderWidth(borderWidth);
        return this;
    }

    @Override
    public ButtonStyle setBorderColor(int borderColor) {
        super.setBorderColor(borderColor);
        return this;
    }

    @Override
    public ButtonStyle setActiveBackground(int activeBackground) {
        super.setActiveBackground(activeBackground);
        return this;
    }

    /**
     * Sets the font for the button text.
     *
     * @param buttonTextFont The name of the font file in the assets directory.
     */
    public ButtonStyle setButtonTextFont(String buttonTextFont) {
        this.buttonTextFont = buttonTextFont;
        return this;
    }

    /**
     * Sets the color for the button text.
     *
     * @param buttonTextColor The color resource ID for the button text.
     */
    public ButtonStyle setButtonTextColor(int buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
        return this;
    }

    /**
     * Sets the appearance for the button text.
     *
     * @param buttonTextAppearance The style resource ID for the button text appearance.
     */
    public ButtonStyle setButtonTextAppearance(int buttonTextAppearance) {
        this.buttonTextAppearance = buttonTextAppearance;
        return this;
    }

    /**
     * Sets the tint color for the button icon.
     *
     * @param buttonIconTint The color resource ID for the button icon tint.
     */
    public ButtonStyle setButtonIconTint(int buttonIconTint) {
        this.buttonIconTint = buttonIconTint;
        return this;
    }

    /**
     * Sets the background color for the button.
     *
     * @param buttonBackgroundColor The color resource ID for the button background.
     */
    public ButtonStyle setButtonBackgroundColor(int buttonBackgroundColor) {
        this.buttonBackgroundColor = buttonBackgroundColor;
        return this;
    }

    /**
     * Sets the background drawable for the button.
     *
     * @param buttonBackgroundDrawable The drawable resource ID for the button background.
     */
    public ButtonStyle setButtonBackgroundDrawable(int buttonBackgroundDrawable) {
        this.buttonBackgroundDrawable = buttonBackgroundDrawable;
        return this;
    }

    /**
     * Sets the size of the button.
     *
     * @param width  The width of the button in pixels.
     * @param height The height of the button in pixels.
     */
    public ButtonStyle setButtonSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public String getButtonTextFont() {
        return buttonTextFont;
    }

    public int getButtonTextColor() {
        return buttonTextColor;
    }

    public int getButtonTextAppearance() {
        return buttonTextAppearance;
    }

    public int getButtonIconTint() {
        return buttonIconTint;
    }

    public int getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    public int getButtonBackgroundDrawable() {
        return buttonBackgroundDrawable;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
