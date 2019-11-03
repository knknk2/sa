import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUo2oCitizen } from 'app/shared/model/uo-2-o-citizen.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './uo-2-o-citizen.reducer';

export interface IUo2oCitizenDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Uo2oCitizenDeleteDialog extends React.Component<IUo2oCitizenDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.uo2oCitizenEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { uo2oCitizenEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="sampleappApp.uo2oCitizen.delete.question">
          <Translate contentKey="sampleappApp.uo2oCitizen.delete.question" interpolate={{ id: uo2oCitizenEntity.id }}>
            Are you sure you want to delete this Uo2oCitizen?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-uo2oCitizen" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ uo2oCitizen }: IRootState) => ({
  uo2oCitizenEntity: uo2oCitizen.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Uo2oCitizenDeleteDialog);
