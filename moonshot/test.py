from pyglet.gl import *
from math import *


def circle(x, y, radius):
    iterations = int(2 * radius * pi)
    s = sin(2 * pi / iterations)
    c = cos(2 * pi / iterations)

    dx, dy = radius, 0

    glBegin(GL_TRIANGLE_FAN)
    glVertex2f(x, y)
    for i in range(iterations + 1):
        glVertex2f(x + dx, y + dy)
        dx, dy = (dx * c - dy * s), (dy * c + dx * s)
    glEnd()


def ship(x, y):
    # Ship is an isosceles triangle with x,y being the center of the shortest side.
    side_length = 40
    vertices = [
        x - side_length * cos(pi / 2.65), y,
        x + side_length * cos(pi / 2.65), y,
        x, y + side_length * sin(pi / 2.65)
    ]
    vertices_gl = (GLfloat * len(vertices))(*vertices)
    glVertexPointer(2, GL_FLOAT, 0, vertices_gl)

    glDrawArrays(GL_TRIANGLES, 0, len(vertices) // 2)



# Direct OpenGL commands to this window.
window = pyglet.window.Window()
glEnableClientState(GL_VERTEX_ARRAY)
fps_display = pyglet.clock.ClockDisplay() # see programming guide pg 48


@window.event
def on_draw():
    fps_display.draw()
    glClear(GL_COLOR_BUFFER_BIT)
    glLoadIdentity()

    circle(window.width // 2, window.height // 2, 40)
    ship(20, 10)


pyglet.app.run()
