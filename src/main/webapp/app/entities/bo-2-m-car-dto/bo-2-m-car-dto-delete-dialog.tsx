import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBo2mCarDTO } from 'app/shared/model/bo-2-m-car-dto.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './bo-2-m-car-dto.reducer';

export interface IBo2mCarDTODeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Bo2mCarDTODeleteDialog extends React.Component<IBo2mCarDTODeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.bo2mCarDTOEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { bo2mCarDTOEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="sampleappApp.bo2mCarDTO.delete.question">
          <Translate contentKey="sampleappApp.bo2mCarDTO.delete.question" interpolate={{ id: bo2mCarDTOEntity.id }}>
            Are you sure you want to delete this Bo2mCarDTO?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-bo2mCarDTO" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ bo2mCarDTO }: IRootState) => ({
  bo2mCarDTOEntity: bo2mCarDTO.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Bo2mCarDTODeleteDialog);
