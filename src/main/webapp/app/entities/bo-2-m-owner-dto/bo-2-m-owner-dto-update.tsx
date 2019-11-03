import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './bo-2-m-owner-dto.reducer';
import { IBo2mOwnerDTO } from 'app/shared/model/bo-2-m-owner-dto.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBo2mOwnerDTOUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBo2mOwnerDTOUpdateState {
  isNew: boolean;
}

export class Bo2mOwnerDTOUpdate extends React.Component<IBo2mOwnerDTOUpdateProps, IBo2mOwnerDTOUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { bo2mOwnerDTOEntity } = this.props;
      const entity = {
        ...bo2mOwnerDTOEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/bo-2-m-owner-dto');
  };

  render() {
    const { bo2mOwnerDTOEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.bo2mOwnerDTO.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.bo2mOwnerDTO.home.createOrEditLabel">Create or edit a Bo2mOwnerDTO</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : bo2mOwnerDTOEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="bo-2-m-owner-dto-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="bo-2-m-owner-dto-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="bo-2-m-owner-dto-name">
                    <Translate contentKey="sampleappApp.bo2mOwnerDTO.name">Name</Translate>
                  </Label>
                  <AvField id="bo-2-m-owner-dto-name" type="text" name="name" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/bo-2-m-owner-dto" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  bo2mOwnerDTOEntity: storeState.bo2mOwnerDTO.entity,
  loading: storeState.bo2mOwnerDTO.loading,
  updating: storeState.bo2mOwnerDTO.updating,
  updateSuccess: storeState.bo2mOwnerDTO.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Bo2mOwnerDTOUpdate);
