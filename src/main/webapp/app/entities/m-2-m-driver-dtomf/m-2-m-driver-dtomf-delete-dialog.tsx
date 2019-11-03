import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IM2mDriverDTOMF } from 'app/shared/model/m-2-m-driver-dtomf.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './m-2-m-driver-dtomf.reducer';

export interface IM2mDriverDTOMFDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class M2mDriverDTOMFDeleteDialog extends React.Component<IM2mDriverDTOMFDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.m2mDriverDTOMFEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { m2mDriverDTOMFEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="sampleappApp.m2mDriverDTOMF.delete.question">
          <Translate contentKey="sampleappApp.m2mDriverDTOMF.delete.question" interpolate={{ id: m2mDriverDTOMFEntity.id }}>
            Are you sure you want to delete this M2mDriverDTOMF?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-m2mDriverDTOMF" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ m2mDriverDTOMF }: IRootState) => ({
  m2mDriverDTOMFEntity: m2mDriverDTOMF.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(M2mDriverDTOMFDeleteDialog);
