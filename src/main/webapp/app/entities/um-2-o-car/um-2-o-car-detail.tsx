import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './um-2-o-car.reducer';
import { IUm2oCar } from 'app/shared/model/um-2-o-car.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUm2oCarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Um2oCarDetail extends React.Component<IUm2oCarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { um2oCarEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.um2oCar.detail.title">Um2oCar</Translate> [<b>{um2oCarEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.um2oCar.name">Name</Translate>
              </span>
            </dt>
            <dd>{um2oCarEntity.name}</dd>
            <dt>
              <Translate contentKey="sampleappApp.um2oCar.um2oOwner">Um 2 O Owner</Translate>
            </dt>
            <dd>{um2oCarEntity.um2oOwner ? um2oCarEntity.um2oOwner.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/um-2-o-car" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/um-2-o-car/${um2oCarEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ um2oCar }: IRootState) => ({
  um2oCarEntity: um2oCar.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Um2oCarDetail);
