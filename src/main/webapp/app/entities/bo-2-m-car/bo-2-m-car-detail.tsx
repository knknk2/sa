import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bo-2-m-car.reducer';
import { IBo2mCar } from 'app/shared/model/bo-2-m-car.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBo2mCarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Bo2mCarDetail extends React.Component<IBo2mCarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { bo2mCarEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.bo2mCar.detail.title">Bo2mCar</Translate> [<b>{bo2mCarEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.bo2mCar.name">Name</Translate>
              </span>
            </dt>
            <dd>{bo2mCarEntity.name}</dd>
            <dt>
              <span id="createdAt">
                <Translate contentKey="sampleappApp.bo2mCar.createdAt">Created At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={bo2mCarEntity.createdAt} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="sampleappApp.bo2mCar.bo2mOwner">Bo 2 M Owner</Translate>
            </dt>
            <dd>{bo2mCarEntity.bo2mOwner ? bo2mCarEntity.bo2mOwner.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/bo-2-m-car" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/bo-2-m-car/${bo2mCarEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ bo2mCar }: IRootState) => ({
  bo2mCarEntity: bo2mCar.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Bo2mCarDetail);
