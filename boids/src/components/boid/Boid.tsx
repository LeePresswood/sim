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
        x: Math.random() * 1920,
        y: Math.random() * 1920,
        angle: Math.random() * 360,
    };

    constructor(props: BoidProps) {
        super(props);
    }

    render() {
        const style: object = {
            position: 'absolute',
            display: 'inline',
            left: this.state.x,
            bottom: this.state.y,
            width: '15px',
            height: '15px',
            borderRadius: '50%',
            backgroundColor: 'blue',
        };

        return (
            <div className="Boid" style={style}/>
        );
    }
}

export default Boid;
