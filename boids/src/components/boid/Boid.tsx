import * as React from 'react';

export interface BoidProps {
    // label: string,
}

type BoidState = {
    x: number,
    y: number,
    angle: number
};

class Boid extends React.Component<BoidProps, BoidState> {
    state: BoidState = {
        x: Math.random(),
        y: Math.random(),
        angle: Math.random()
    };

    constructor(props: BoidProps) {
        super(props);
    }

    render() {
        return (
            <div className="Boid" style={this.getBoidStyle()}/>
        );
    }

    private getBoidStyle() {
        return {
            display: 'inline',
            x: this.state.x,
            y: this.state.y,
            width: '100px',
            height: '100px',
        };
    }
}

export default Boid;
