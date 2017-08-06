from pyglet.gl import *
from math import *


def circle(x, y, radius):
    iterations = int(2*radius*pi)
    s = sin(2*pi / iterations)
    c = cos(2*pi / iterations)

    dx, dy = radius, 0

    glBegin(GL_TRIANGLE_FAN)
    glVertex2f(x, y)
    for i in range(iterations+1):
        glVertex2f(x+dx, y+dy)
        dx, dy = (dx*c - dy*s), (dy*c + dx*s)
    glEnd()

def ship(x, y):
    # Ship is an equilateral triangle centered around x,y
    side_length = 10
    p1 = (x - 0.866 * side_length, y - 0.5 * side_length)
    p2 = (x + 0.866 * side_length, y - 0.5 * side_length)
    p3 = (x, y + 1.0 * side_length)

# Direct OpenGL commands to this window.
window = pyglet.window.Window()


@window.event
def on_draw():
    glClear(GL_COLOR_BUFFER_BIT)
    glLoadIdentity()

    circle(window.width // 2, window.height // 2, 40)


pyglet.app.run()
