import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './to-2-m-car.reducer';
import { ITo2mCar } from 'app/shared/model/to-2-m-car.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITo2mCarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class To2mCarDetail extends React.Component<ITo2mCarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { to2mCarEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.to2mCar.detail.title">To2mCar</Translate> [<b>{to2mCarEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.to2mCar.name">Name</Translate>
              </span>
            </dt>
            <dd>{to2mCarEntity.name}</dd>
            <dt>
              <Translate contentKey="sampleappApp.to2mCar.to2mOwner">To 2 M Owner</Translate>
            </dt>
            <dd>{to2mCarEntity.to2mOwnerId ? to2mCarEntity.to2mOwnerId : ''}</dd>
            <dt>
              <Translate contentKey="sampleappApp.to2mCar.to2mDriver">To 2 M Driver</Translate>
            </dt>
            <dd>{to2mCarEntity.to2mDriverId ? to2mCarEntity.to2mDriverId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/to-2-m-car" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/to-2-m-car/${to2mCarEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ to2mCar }: IRootState) => ({
  to2mCarEntity: to2mCar.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(To2mCarDetail);
