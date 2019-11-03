import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUo2oPassport } from 'app/shared/model/uo-2-o-passport.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './uo-2-o-passport.reducer';

export interface IUo2oPassportDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Uo2oPassportDeleteDialog extends React.Component<IUo2oPassportDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.uo2oPassportEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { uo2oPassportEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="sampleappApp.uo2oPassport.delete.question">
          <Translate contentKey="sampleappApp.uo2oPassport.delete.question" interpolate={{ id: uo2oPassportEntity.id }}>
            Are you sure you want to delete this Uo2oPassport?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-uo2oPassport" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ uo2oPassport }: IRootState) => ({
  uo2oPassportEntity: uo2oPassport.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Uo2oPassportDeleteDialog);
