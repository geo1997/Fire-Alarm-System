import React, {Component} from 'react';
import * as ReactDOM from 'react-dom';
import {
    RadialGauge
} from '@progress/kendo-react-gauges';
import '@progress/kendo-theme-default/dist/all.css';

class Sensor extends Component {
    state = {
        isLoading: true,
        alarms: [],
        value: 0
    }

    async componentDidMount() {
        const response = await fetch('/alarms')
        const body = await response.json();
        this.setState({alarms : body,isLoading: false});
        setInterval(
            () => {
                this.setState({
                    value: 0
                });
            },
            4000);
    }

    render() {
        const {alarms,isLoading} = this.state;
        if (isLoading)
            return (<div>Loading...</div>)
        const radialOptions = {
            pointer: {
                value: this.state.value
            }
        };

        return (
            <div>
                <h2 className="text-center">Sensor</h2>
                <div className="row ml-3 mr-3">
                    {
                        alarms.map(alarm =>
                            <div className="col-sm-4 mt-3">
                                <div className="card">
                                    <div className="card-body" id={alarm.id}>
                                        <h3 className="card-title text-center">Sensor {alarm.id}</h3>
                                        <h5 className="text-sm">Floor No. : {alarm.floorNum}</h5>
                                        <h5>Floor No. : {alarm.roomNum}</h5>
                                        <div className="w-50 mb-2" style={{marginLeft:'25%'}}>
                                            <RadialGauge {...{value:alarm.co2level*10}}/>
                                            {alarm.co2level>5 ? <div className="text-center text-danger">CO<small>2</small> Level : {alarm.co2level}</div> :
                                                <div className="text-center text-success">CO<small>2</small> Level : {alarm.co2level}</div>}
                                        </div>
                                        <div className="w-50" style={{marginLeft:'25%'}}>
                                            <RadialGauge {...{value:alarm.smokeLevel*10}}/>
                                            {alarm.smokeLevel>5 ? <div className="text-center text-danger">Smoke Level : {alarm.smokeLevel}</div> :
                                                <div className="text-center text-success">Smoke Level : {alarm.smokeLevel}</div>}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        )}
                </div>
            </div>
        );
    }
}



export default Sensor;
