import React, {Component} from 'react';

class Sensor extends Component {
    state = {
        isLoading: true,
        alarms: []
    }

    async componentDidMount() {
        const response = await fetch('/alarms')
        const body = await response.json();
        this.setState({alarms : body,isLoading: false});
    }

    render() {
        const {alarms,isLoading} = this.state;
        if (isLoading)
            return (<div>Loading...</div>)
        return (
            <div>
                <h2>Sensor</h2>
                {
                    alarms.map(alarm =>
                        <div id={alarm.id}>
                            {alarm.co2level}
                            {alarm.smoke_level}
                        </div>
                    )
                }
                <div className="row">
                    <div className="col-sm-3">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Special title treatment</h5>
                                <p className="card-text">With supporting text below as a natural lead-in to additional
                                    content.</p>
                                <a href="#" className="btn btn-primary">Go somewhere</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Sensor;
