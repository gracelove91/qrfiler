"""Generate a macOS app icon for qrfiler using Pillow."""
from PIL import Image, ImageDraw
import os
import subprocess

SIZE = 1024
BG = "#1E2A3A"
ACCENT = "#4FC3F7"
ACCENT2 = "#81C784"

def round_rect(draw, xy, radius, fill):
    """Draw a rounded rectangle."""
    x1, y1, x2, y2 = xy
    draw.rounded_rectangle(xy, radius=radius, fill=fill)

def draw_qr_pattern(draw, cx, cy, size, color):
    """Draw a simplified QR-like pattern."""
    step = size // 7
    for i in range(7):
        for j in range(7):
            if (i + j) % 3 == 0 or (i == 0 or i == 6 or j == 0 or j == 6):
                draw.rectangle(
                    [cx - size//2 + i*step, cy - size//2 + j*step,
                     cx - size//2 + i*step + step - 4, cy - size//2 + j*step + step - 4],
                    fill=color
                )

def draw_arrow(draw, cx, cy, size, color):
    """Draw an upward arrow."""
    # arrow head
    draw.polygon([
        (cx, cy - size//2),
        (cx - size//3, cy),
        (cx + size//3, cy),
    ], fill=color)
    # arrow body
    draw.rectangle([
        cx - size//8, cy,
        cx + size//8, cy + size//2
    ], fill=color)

# Create canvas
img = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
draw = ImageDraw.Draw(img)

# Rounded background (macOS Big Sur style)
padding = SIZE // 16
round_rect(draw, [padding, padding, SIZE-padding, SIZE-padding], radius=SIZE//5, fill=BG)

# Gradient overlay (simulate with blend)
overlay = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
overlay_draw = ImageDraw.Draw(overlay)
for i in range(SIZE):
    alpha = int(30 * (1 - i / SIZE))
    overlay_draw.line([(0, i), (SIZE, i)], fill=(0, 0, 0, alpha))
img = Image.alpha_composite(img, overlay)
draw = ImageDraw.Draw(img)

# Border highlight
draw.rounded_rectangle([padding, padding, SIZE-padding, SIZE-padding], radius=SIZE//5, outline=ACCENT, width=4)

# QR pattern
center = SIZE // 2
qr_size = SIZE * 0.55
draw_qr_pattern(draw, center, center, int(qr_size), ACCENT)

# Arrow on top
arrow_size = int(qr_size * 0.35)
draw_arrow(draw, center, center, arrow_size, "#FFFFFF")

# Corner accent dots
dot_r = SIZE // 25
draw.ellipse([padding*2-dot_r, padding*2-dot_r, padding*2+dot_r, padding*2+dot_r], fill=ACCENT2)
draw.ellipse([SIZE-padding*2-dot_r, padding*2-dot_r, SIZE-padding*2+dot_r, padding*2+dot_r], fill=ACCENT2)
draw.ellipse([padding*2-dot_r, SIZE-padding*2-dot_r, padding*2+dot_r, SIZE-padding*2+dot_r], fill=ACCENT2)
draw.ellipse([SIZE-padding*2-dot_r, SIZE-padding*2-dot_r, SIZE-padding*2+dot_r, SIZE-padding*2+dot_r], fill=ACCENT2)

# Save master PNG
iconset_dir = "build/icon.iconset"
os.makedirs(iconset_dir, exist_ok=True)

sizes = [16, 32, 64, 128, 256, 512, 1024]
for s in sizes:
    resized = img.resize((s, s), Image.LANCZOS)
    resized.save(f"{iconset_dir}/icon_{s}x{s}.png")
    if s <= 512:
        resized2x = img.resize((s*2, s*2), Image.LANCZOS)
        resized2x.save(f"{iconset_dir}/icon_{s}x{s}@2x.png")

# Convert to .icns
icns_path = "src/main/resources/qrfiler.icns"
os.makedirs("src/main/resources", exist_ok=True)
subprocess.run(["iconutil", "-c", "icns", iconset_dir, "-o", icns_path], check=True)

print(f"Icon generated: {icns_path}")
