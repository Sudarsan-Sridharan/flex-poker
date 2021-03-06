import React from 'react';
import WebSocketService from '../webSocket/WebSocketService';
import WebSocketSubscriptionManager from '../webSocket/WebSocketSubscriptionManager';
import CommonCards from './CommonCards';
import MyCards from './MyCards';
import Seat from './Seat';
import PokerActions from './PokerActions';
import SeatContainer from './SeatContainer';
import Chat from '../common/Chat';
import _ from 'lodash';

export default React.createClass({

  getInitialState() {
    return {
      myLeftCardId: null,
      myRightCardId: null,
      totalPot: 0,
      visibleCommonCards: [],
      seats: [],
      tableVersion: 0,
      pots: []
    }
  },

  componentDidMount() {
    const gameId = this.props.params.gameId;
    const tableId = this.props.params.tableId;

    WebSocketSubscriptionManager.subscribe(this, [
      {location: `/topic/game/${gameId}/table/${tableId}`, subscription: receiveTableUpdate.bind(this)},
      {location: `/topic/chat/game/${gameId}/table/${tableId}/user`, subscription: displayChat.bind(this)},
      {location: `/topic/chat/game/${gameId}/table/${tableId}/system`, subscription: displayChat.bind(this)}
    ]);

    document.addEventListener(`pocketCardsReceived-${tableId}`, evt => {
      this.setState({
        myLeftCardId: evt.detail.cardId1,
        myRightCardId: evt.detail.cardId2
      })
    });

  },

  componentWillUnmount() {
    WebSocketSubscriptionManager.unsubscribe(this);
  },

  render() {
    const username = window.username;
    const mySeat = this.state.seats.find(seat => seat.name === username);

    return (
      <div>
        <div className={"poker-table"}>
          <div>{this.state.totalPot}</div>
          <CommonCards visibleCommonCards={this.state.visibleCommonCards} />
          <SeatContainer gameId={this.props.params.gameId} tableId={this.props.params.tableId} mySeat={mySeat} seats={this.state.seats} />
        </div>

        {this.state.pots.map(pot => {
          return(
            <div>
              <div>{pot.seats}</div>
              <div>{pot.amount}</div>
              <div>{pot.winners}</div>
              <div>{pot.open ? 'open' : 'closed'}</div>
            </div>
          )
        })}

        <MyCards myLeftCardId={this.state.myLeftCardId} myRightCardId={this.state.myRightCardId} />
        {
          _.isNil(mySeat)
            ? null
            : <PokerActions
                gameId={this.props.params.gameId}
                tableId={this.props.params.tableId}
                actionOn={mySeat.actionOn}
                callAmount={mySeat.callAmount}
                minRaiseTo={mySeat.raiseTo}
                maxRaiseTo={mySeat.chipsInBack + mySeat.chipsInFront} />
        }

        <Chat ref="tableChat" sendChat={sendChat.bind(this, this.props.params.gameId, this.props.params.tableId)} />
      </div>
    )
  }

})

function displayChat(message) {
  this.refs.tableChat.displayChat(message.body);
}

function sendChat(gameId, tableId, message) {
  const tableMessage = {
    message: message,
    receiverUsernames: null,
    gameId: gameId,
    tableId: tableId
  };

  WebSocketService.send('/app/sendchatmessage', tableMessage);
}

function receiveTableUpdate(message) {
  let table = JSON.parse(message.body);

  if (table.version > this.state.tableVersion) {
    this.setState({
      totalPot: table.totalPot,
      visibleCommonCards: table.visibleCommonCards,
      seats: table.seats,
      tableVersion: table.version,
      pots: table.pots
    });
  }
}
