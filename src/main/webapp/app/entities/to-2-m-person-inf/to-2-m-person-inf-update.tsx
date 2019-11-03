import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './to-2-m-person-inf.reducer';
import { ITo2mPersonInf } from 'app/shared/model/to-2-m-person-inf.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITo2mPersonInfUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITo2mPersonInfUpdateState {
  isNew: boolean;
}

export class To2mPersonInfUpdate extends React.Component<ITo2mPersonInfUpdateProps, ITo2mPersonInfUpdateState> {
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
      const { to2mPersonInfEntity } = this.props;
      const entity = {
        ...to2mPersonInfEntity,
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
    this.props.history.push('/entity/to-2-m-person-inf');
  };

  render() {
    const { to2mPersonInfEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleappApp.to2mPersonInf.home.createOrEditLabel">
              <Translate contentKey="sampleappApp.to2mPersonInf.home.createOrEditLabel">Create or edit a To2mPersonInf</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : to2mPersonInfEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="to-2-m-person-inf-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="to-2-m-person-inf-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="to-2-m-person-inf-name">
                    <Translate contentKey="sampleappApp.to2mPersonInf.name">Name</Translate>
                  </Label>
                  <AvField id="to-2-m-person-inf-name" type="text" name="name" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/to-2-m-person-inf" replace color="info">
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
  to2mPersonInfEntity: storeState.to2mPersonInf.entity,
  loading: storeState.to2mPersonInf.loading,
  updating: storeState.to2mPersonInf.updating,
  updateSuccess: storeState.to2mPersonInf.updateSuccess
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
)(To2mPersonInfUpdate);
