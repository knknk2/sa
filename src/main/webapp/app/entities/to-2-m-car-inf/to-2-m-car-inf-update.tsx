import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITo2mPersonInf } from 'app/shared/model/to-2-m-person-inf.model';
import { getEntities as getTo2MPersonInfs } from 'app/entities/to-2-m-person-inf/to-2-m-person-inf.reducer';
import { getEntity, updateEntity, createEntity, reset } from './to-2-m-car-inf.reducer';
import { ITo2mCarInf } from 'app/shared/model/to-2-m-car-inf.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITo2mCarInfUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITo2mCarInfUpdateState {
  isNew: boolean;
  to2mOwnerInfId: string;
  to2mDriverInfId: string;
}

export class To2mCarInfUpdate extends React.Component<ITo2mCarInfUpdateProps, ITo2mCarInfUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      to2mOwnerInfId: '0',
      to2mDriverInfId: '0',
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

    this.props.getTo2MPersonInfs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { to2mCarInfEntity } = this.props;
      const entity = {
        ...to2mCarInfEntity,
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
    this.props.history.push('/entity/to-2-m-car-inf');
  };

  render() {
    const { to2mCarInfEntity, to2mPersonInfs, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.to2mCarInf.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.to2mCarInf.home.createOrEditLabel">Create or edit a To2mCarInf</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : to2mCarInfEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="to-2-m-car-inf-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="to-2-m-car-inf-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="to-2-m-car-inf-name">
                    <Translate contentKey="sampleappApp.to2mCarInf.name">Name</Translate>
                  </Label>
                  <AvField id="to-2-m-car-inf-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label for="to-2-m-car-inf-to2mOwnerInf">
                    <Translate contentKey="sampleappApp.to2mCarInf.to2mOwnerInf">To 2 M Owner Inf</Translate>
                  </Label>
                  <AvInput id="to-2-m-car-inf-to2mOwnerInf" type="select" className="form-control" name="to2mOwnerInfId">
                    <option value="" key="0" />
                    {to2mPersonInfs
                      ? to2mPersonInfs.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="to-2-m-car-inf-to2mDriverInf">
                    <Translate contentKey="sampleappApp.to2mCarInf.to2mDriverInf">To 2 M Driver Inf</Translate>
                  </Label>
                  <AvInput id="to-2-m-car-inf-to2mDriverInf" type="select" className="form-control" name="to2mDriverInfId">
                    <option value="" key="0" />
                    {to2mPersonInfs
                      ? to2mPersonInfs.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/to-2-m-car-inf" replace color="info">
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
  to2mPersonInfs: storeState.to2mPersonInf.entities,
  to2mCarInfEntity: storeState.to2mCarInf.entity,
  loading: storeState.to2mCarInf.loading,
  updating: storeState.to2mCarInf.updating,
  updateSuccess: storeState.to2mCarInf.updateSuccess
});

const mapDispatchToProps = {
  getTo2MPersonInfs,
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
)(To2mCarInfUpdate);
