import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IM2mCarDTOMF } from 'app/shared/model/m-2-m-car-dtomf.model';
import { getEntities as getM2MCarDtomfs } from 'app/entities/m-2-m-car-dtomf/m-2-m-car-dtomf.reducer';
import { getEntity, updateEntity, createEntity, reset } from './m-2-m-driver-dtomf.reducer';
import { IM2mDriverDTOMF } from 'app/shared/model/m-2-m-driver-dtomf.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IM2mDriverDTOMFUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IM2mDriverDTOMFUpdateState {
  isNew: boolean;
  m2mCarDTOMFId: string;
}

export class M2mDriverDTOMFUpdate extends React.Component<IM2mDriverDTOMFUpdateProps, IM2mDriverDTOMFUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      m2mCarDTOMFId: '0',
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

    this.props.getM2MCarDtomfs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { m2mDriverDTOMFEntity } = this.props;
      const entity = {
        ...m2mDriverDTOMFEntity,
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
    this.props.history.push('/entity/m-2-m-driver-dtomf');
  };

  render() {
    const { m2mDriverDTOMFEntity, m2mCarDTOMFS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.m2mDriverDTOMF.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.m2mDriverDTOMF.home.createOrEditLabel">Create or edit a M2mDriverDTOMF</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : m2mDriverDTOMFEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="m-2-m-driver-dtomf-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="m-2-m-driver-dtomf-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="m-2-m-driver-dtomf-name">
                    <Translate contentKey="sampleappApp.m2mDriverDTOMF.name">Name</Translate>
                  </Label>
                  <AvField id="m-2-m-driver-dtomf-name" type="text" name="name" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/m-2-m-driver-dtomf" replace color="info">
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
  m2mCarDTOMFS: storeState.m2mCarDTOMF.entities,
  m2mDriverDTOMFEntity: storeState.m2mDriverDTOMF.entity,
  loading: storeState.m2mDriverDTOMF.loading,
  updating: storeState.m2mDriverDTOMF.updating,
  updateSuccess: storeState.m2mDriverDTOMF.updateSuccess
});

const mapDispatchToProps = {
  getM2MCarDtomfs,
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
)(M2mDriverDTOMFUpdate);
