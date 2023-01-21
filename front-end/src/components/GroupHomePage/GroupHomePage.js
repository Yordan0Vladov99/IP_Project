import { render } from "@testing-library/react"
import React from "react"
import GroupForm from "../Group/GroupForm/GroupForm";
import TopGroups from "../TopGroups/TopGroups"
import UserGroups from "../UserGroups/UserGroups"
import './GroupHomePage.css'

export default class GroupHomePage extends React.Component {
    state = {
        seen: false
      };
    
      togglePop = () => {
        this.setState({
          seen: !this.state.seen
        });
      };
    render(){
        return(
            <div>
                <h1>Your Groups</h1>
                <UserGroups/>
                <h1>Top Groups</h1>
                <TopGroups/>
                <div>
                  <div className="btn" onClick={this.togglePop}>
                  <button>New User?</button>
                  </div>
                  {this.state.seen ? <GroupForm toggle={this.togglePop} /> : null}
                </div>

                
            </div>
        )
    }
}