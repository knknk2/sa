import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './m-2-m-car.reducer';
import { IM2mCar } from 'app/shared/model/m-2-m-car.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IM2mCarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class M2mCarDetail extends React.Component<IM2mCarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { m2mCarEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.m2mCar.detail.title">M2mCar</Translate> [<b>{m2mCarEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.m2mCar.name">Name</Translate>
              </span>
            </dt>
            <dd>{m2mCarEntity.name}</dd>
            <dt>
              <Translate contentKey="sampleappApp.m2mCar.m2mDriver">M 2 M Driver</Translate>
            </dt>
            <dd>
              {m2mCarEntity.m2mDrivers
                ? m2mCarEntity.m2mDrivers.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === m2mCarEntity.m2mDrivers.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/m-2-m-car" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/m-2-m-car/${m2mCarEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ m2mCar }: IRootState) => ({
  m2mCarEntity: m2mCar.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(M2mCarDetail);
