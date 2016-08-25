import React from 'react';
import WebSocketService from '../webSocket/WebSocketService';
import { Modal, Button, FormControl, FormGroup, ControlLabel } from 'react-bootstrap';

export default ({showModal, hideDialog}) => {

  const preventNonNumeric = (evt) => {
    if (!(evt.which >= 48 && evt.which <= 57)) {
      evt.preventDefault();
    }
  };

  const createGameFormSubmitted = (evt) => {
    evt.preventDefault();
    const nameElement = evt.target.elements[0];
    const playersElement = evt.target.elements[1];
    const playersPerTableElement = evt.target.elements[2];
    const numberOfMinutesBetweenBlindLevelsElement = evt.target.elements[3];

    WebSocketService.send('/app/creategame', {
      name: nameElement.value,
      players: playersElement.value,
      playersPerTable: playersPerTableElement.value,
      numberOfMinutesBetweenBlindLevels: numberOfMinutesBetweenBlindLevelsElement.value
    });

    hideDialog();
  };

 return (
    <Modal show={showModal} onHide={hideDialog}>
      <Modal.Header>
        <button className={"close"} onClick={hideDialog}>X</button>
        <Modal.Title>Create Game</Modal.Title>
      </Modal.Header>
      <form id="create-game-form" onSubmit={createGameFormSubmitted}>
        <Modal.Body>
          <FormGroup>
            <ControlLabel>Name</ControlLabel>
            <FormControl type="text" name="name" autoFocus />
          </FormGroup>
          <FormGroup>
            <ControlLabel>Number of Players</ControlLabel>
            <FormControl type="text" name="players" onKeyPress={preventNonNumeric} />
          </FormGroup>
          <FormGroup>
            <ControlLabel>Number of Players per Table</ControlLabel>
            <FormControl type="text" name="playersPerTable" onKeyPress={preventNonNumeric} />
          </FormGroup>
          <FormGroup>
            <ControlLabel>Blind increment (minutes)</ControlLabel>
            <FormControl type="text" name="numberOfMinutesBetweenBlindLevels" onKeyPress={preventNonNumeric} />
          </FormGroup>
        </Modal.Body>
        <Modal.Footer>
          <Button onClick={hideDialog}>Close</Button>
          <Button type="submit" bsStyle="primary">Create Game</Button>
        </Modal.Footer>
      </form>
    </Modal>
  );

}
