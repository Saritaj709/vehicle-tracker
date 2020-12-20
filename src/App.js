import './App.css';
import React,{ Component } from 'react';
import ReactDOM from 'react-dom';

class App extends Component {
   constructor(props){
     super(props);
     this.state={
       items: [],
       isLoaded: false,
     }
   }
   componentDidMount(){
    let items= [];
      fetch('http://localhost:8080/api/catchViolatedVehicles?currentTrafficLocation=NH road',{
        method: 'POST'})
      .then(res=>res.json())
      .then(data=>{
           this.setState({
             isLoaded: true,
             items: data,
           })
      });
  }
  checkViolations() {
    alert("Vehicles with Violations!");
  }
  render(){
     
    var { isLoaded, items } = this.state;
    if(!isLoaded){
      return <div>Loading...</div>;
    }
    else{
      return (
         <div className="App">
           <style>{'body { background-color: green; }'}</style>
           <form>
      </form>
      <button onClick={this.checkviolations}><br></br>
          <ul>
          <p1 style={{color: "red"}}> Message: {items.message}<br></br></p1>
          <p1 style={{color: "red"}}> Status: {items.status}</p1>
          </ul>
          </button>
         </div>
      );
    }
  }  
}
ReactDOM.render(<App />, document.getElementById('root'));
export default App;
