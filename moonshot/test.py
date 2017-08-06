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
    # Ship is an equilateral triangle centered around x,y
    side_length = 10
    vertices = [
        x - 0.866 * side_length, y - 0.5 * side_length,
        x + 0.866 * side_length, y - 0.5 * side_length,
        x, y + 1.0 * side_length
    ]
    vertices_gl = (GLfloat * len(vertices))(*vertices)
    glVertexPointer(2, GL_FLOAT, 0, vertices_gl)

    glDrawArrays(GL_TRIANGLES, 0, len(vertices) // 2)



# Direct OpenGL commands to this window.
window = pyglet.window.Window()
glEnableClientState(GL_VERTEX_ARRAY)


@window.event
def on_draw():
    glClear(GL_COLOR_BUFFER_BIT)
    glLoadIdentity()

    circle(window.width // 2, window.height // 2, 40)
    ship(20, 10)


pyglet.app.run()
